package dev.eministar.logium.commands;

import dev.eministar.logium.config.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

public class ReportCommand implements CommandExecutor, TabCompleter {

    private final FileConfiguration config;
    private final LanguageManager lang;

    public ReportCommand(FileConfiguration config, LanguageManager lang) {
        this.config = config;
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cNur Spieler können diesen Befehl ausführen.");
            return true;
        }

        ConfigurationSection reportSection = config.getConfigurationSection("logs.report");
        if (reportSection == null || !reportSection.getBoolean("enabled", false)) {
            p.sendMessage(prefix() + "§cDas Report-System ist derzeit deaktiviert.");
            return true;
        }

        if (!p.hasPermission("logium.report.use")) {
            p.sendMessage(prefix() + lang.getMessage("no-permission"));
            return true;
        }

        if (args.length < 2) {
            p.sendMessage(prefix() + lang.getMessage("usage-header"));
            p.sendMessage("§7/report <Spieler> <Grund>");
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null || !target.isOnline()) {
            p.sendMessage(prefix() + "§cDieser Spieler ist nicht online.");
            return true;
        }

        if (target.equals(p)) {
            p.sendMessage(prefix() + "§cDu kannst dich nicht selbst melden.");
            return true;
        }

        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        // Ingame-Broadcast
        Map<String, String> placeholders = Map.of(
                "reporter", p.getName(),
                "target", target.getName(),
                "reason", reason
        );
        String broadcastMsg = lang.getMessage("report.broadcast", placeholders);
        Bukkit.getOnlinePlayers().stream()
                .filter(pl -> pl.hasPermission("logium.report.notify"))
                .forEach(pl -> pl.sendMessage(prefix() + broadcastMsg));

        // Rückmeldung an Melder
        p.sendMessage(prefix() + lang.getMessage("report.sent"));

        // Discord Webhook
        String webhookUrl = reportSection.getString("webhook-url");
        boolean simplified = reportSection.getBoolean("simplified-message", false);
        if (webhookUrl != null && !webhookUrl.isEmpty()) {
            sendToDiscord(p, target, reason, time, webhookUrl, simplified);
        }

        return true;
    }

    private void sendToDiscord(Player reporter, Player target, String reason, String time, String webhookUrl, boolean simplified) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String json;

            if (simplified) {
                String simple = lang.getMessage("report.discord.simple", Map.of(
                        "target", target.getName(),
                        "reporter", reporter.getName(),
                        "reason", reason,
                        "time", time
                ));

                json = """
            {
              "content": "%s"
            }
            """.formatted(escapeJson(simple));

            } else {
                String embedDesc = escapeJson(lang.getMessage("report.discord.embed", Map.of(
                        "target", target.getName(),
                        "target_uuid", target.getUniqueId().toString(),
                        "reporter", reporter.getName(),
                        "reporter_uuid", reporter.getUniqueId().toString(),
                        "reason", reason,
                        "time", time
                )));
                String title = escapeJson(lang.getMessage("report.discord.title"));

                json = """
            {
              "embeds": [
                {
                  "title": "%s",
                  "color": 16753920,
                  "description": "%s",
                  "footer": {
                    "text": "Logium Report System"
                  },
                  "timestamp": "%s"
                }
              ]
            }
            """.formatted(title, embedDesc, java.time.Instant.now().toString());
            }

            System.out.println("📤 JSON an Discord:\n" + json);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int response = connection.getResponseCode();
            System.out.println("✅ Discord-Webhook gesendet, Response: " + response);

            connection.disconnect();
        } catch (Exception e) {
            Bukkit.getLogger().severe("[Logium] Fehler beim Discord-Webhook:");
            e.printStackTrace();
        }
    }

    private String prefix() {
        return lang.getMessage("prefix");
    }

    private String escapeJson(String input) {
        return input
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", ""); // optional, aber sauberer
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            String input = args[0].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(input))
                    .sorted()
                    .toList();
        }

        return Collections.emptyList();
    }
}