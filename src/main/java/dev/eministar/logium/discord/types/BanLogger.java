package dev.eministar.logium.discord.types;

import dev.eministar.logium.Logium;
import dev.eministar.logium.discord.DiscordWebhookSender;

import java.util.HashMap;
import java.util.Map;

public class BanLogger {

    public static void log(String bannedPlayer, String reason) {
        Logium plugin = Logium.getInstance();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("player", bannedPlayer);
        placeholders.put("reason", reason);

        String title = plugin.getLanguageManager().getMessage("ban.title", placeholders);
        String content = plugin.getLanguageManager().getMessage("ban.content", placeholders);

        DiscordWebhookSender.send(
                "ban",
                bannedPlayer,
                title,
                content
        );
    }
}
