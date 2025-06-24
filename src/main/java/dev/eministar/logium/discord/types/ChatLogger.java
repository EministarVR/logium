package dev.eministar.logium.discord.types;

import dev.eministar.logium.Logium;
import dev.eministar.logium.discord.DiscordWebhookSender;

import java.util.HashMap;
import java.util.Map;

public class ChatLogger {

    public static void log(String playerName, String messageContent) {
        Logium plugin = Logium.getInstance();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("player", playerName);
        placeholders.put("message", messageContent);

        String title = plugin.getLanguageManager().getMessage("chat.title", placeholders);
        String content = plugin.getLanguageManager().getMessage("chat.content", placeholders);

        DiscordWebhookSender.send(
                "chat",
                playerName,
                title,
                content
        );
    }
}
