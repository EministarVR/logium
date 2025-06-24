package dev.eministar.logium.discord.types;

import dev.eministar.logium.Logium;
import dev.eministar.logium.discord.DiscordWebhookSender;

import java.util.HashMap;
import java.util.Map;

public class JoinLeaveLogger {

    public static void logJoin(String playerName) {
        Logium plugin = Logium.getInstance();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("player", playerName);

        String title = plugin.getLanguageManager().getMessage("join.title", placeholders);
        String content = plugin.getLanguageManager().getMessage("join.content", placeholders);

        DiscordWebhookSender.send(
                "join-leave",
                playerName,
                title,
                content
        );
    }

    public static void logLeave(String playerName) {
        Logium plugin = Logium.getInstance();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("player", playerName);

        String title = plugin.getLanguageManager().getMessage("leave.title", placeholders);
        String content = plugin.getLanguageManager().getMessage("leave.content", placeholders);

        DiscordWebhookSender.send(
                "join-leave",
                playerName,
                title,
                content
        );
    }
}
