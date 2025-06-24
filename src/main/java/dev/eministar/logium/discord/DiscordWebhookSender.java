package dev.eministar.logium.discord;

import dev.eministar.logium.Logium;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class DiscordWebhookSender {

    private static final String SKIN_API = "https://mc-heads.net/avatar/"; // Skin-Image API
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static void send(String typeKey, String playerName, String messageTitle, String messageContent) {
        String webhookUrl = Logium.getInstance().getConfigManager().getWebhook(typeKey);
        if (webhookUrl == null || webhookUrl.isEmpty()) return;

        boolean simplified = Logium.getInstance().getConfigManager().isSimplified(typeKey);

        if (simplified) {
            sendSimpleWebhook(webhookUrl, "**" + playerName + "**: " + messageContent);
        } else {
            sendEmbedWebhook(webhookUrl, playerName, messageTitle, messageContent);
        }
    }

    private static void sendSimpleWebhook(String webhookUrl, String message) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = "{\"content\": \"" + message.replace("\"", "\\\"") + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            conn.getInputStream().close();
        } catch (Exception e) {
            Bukkit.getLogger().warning("[Logium] Fehler beim Senden einfacher Webhook: " + e.getMessage());
        }
    }

    private static void sendEmbedWebhook(String webhookUrl, String playerName, String title, String content) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String timestamp = FORMAT.format(new Date());
            String skinUrl = SKIN_API + playerName;

            String json = "{\n" +
                    "  \"embeds\": [\n" +
                    "    {\n" +
                    "      \"title\": \"" + escape(title) + "\",\n" +
                    "      \"description\": \"" + escape(content) + "\",\n" +
                    "      \"color\": 5814783,\n" +
                    "      \"thumbnail\": {\n" +
                    "        \"url\": \"" + skinUrl + "\"\n" +
                    "      },\n" +
                    "      \"footer\": {\n" +
                    "        \"text\": \"Logium Logging • " + timestamp + "\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            conn.getInputStream().close();
        } catch (Exception e) {
            Bukkit.getLogger().warning("[Logium] Fehler beim Senden Embed-Webhook: " + e.getMessage());
        }
    }

    private static String escape(String input) {
        return input.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
