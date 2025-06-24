package dev.eministar.logium.discord.types;

import dev.eministar.logium.Logium;
import dev.eministar.logium.discord.DiscordWebhookSender;

import java.util.HashMap;
import java.util.Map;

public class MuteLogger {

    public static void log(String mutedPlayer, String reason) {
        Logium plugin = Logium.getInstance();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("player", mutedPlayer);
        placeholders.put("reason", reason);

        String title = plugin.getLanguageManager().getMessage("mute.title", placeholders);
        String content = plugin.getLanguageManager().getMessage("mute.content", placeholders);

        DiscordWebhookSender.send(
                "mute",
                mutedPlayer,
                title,
                content
        );
    }
}
