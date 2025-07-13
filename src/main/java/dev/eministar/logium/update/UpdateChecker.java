package dev.eministar.logium.update;

import dev.eministar.logium.Logium;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    private final Logium plugin;
    private final String updateUrl = "https://dcs.lol/logium-version.txt";
    private final String downloadUrl = "https://www.spigotmc.org/resources/logium-–-insightful-server-logs-delivered-in-style.126967/";

    public UpdateChecker(Logium plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(updateUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()));
                if (scanner.hasNextLine()) {
                    String latestVersion = scanner.nextLine().trim();
                    String currentVersion = plugin.getDescription().getVersion().trim();

                    if (!latestVersion.equalsIgnoreCase(currentVersion)) {
                        String msg1 = "§8[§dLogium§8] §eEs ist ein §cUpdate §everfügbar!";
                        String msg2 = "§8[§dLogium§8] §7Aktuell: §f" + currentVersion + " §8| §aNeu: §f" + latestVersion;
                        String msg3 = "§8[§dLogium§8] §7Download: §b" + downloadUrl;

                        // Konsole log
                        plugin.getLogger().warning("===========================================");
                        plugin.getLogger().warning("   Es ist ein Update für Logium verfügbar!");
                        plugin.getLogger().warning("   Aktuelle Version: " + currentVersion);
                        plugin.getLogger().warning("   Neueste Version:  " + latestVersion);
                        plugin.getLogger().warning("   Lade es herunter: " + downloadUrl);
                        plugin.getLogger().warning("===========================================");

                        // Nachricht an alle OPs oder Spieler mit Permission
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            Bukkit.getOnlinePlayers().forEach(player -> {
                                if (player.isOp() || player.hasPermission("logium.update")) {
                                    player.sendMessage(msg1);
                                    player.sendMessage(msg2);
                                    player.sendMessage(msg3);
                                }
                            });
                        });
                    } else {
                        plugin.getLogger().info("Logium ist auf dem neuesten Stand.");
                    }
                }
                scanner.close();
            } catch (Exception e) {
                plugin.getLogger().warning("Konnte nicht nach Updates suchen: " + e.getMessage());
            }
        });
    }
}
