package dev.eministar.logium.discord.types;

import dev.eministar.logium.Logium;
import dev.eministar.logium.discord.DiscordWebhookSender;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class TeleportLogger {

    public static void logTeleport(String playerName, Location to) {
        Logium plugin = Logium.getInstance();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("world", to.getWorld() != null ? to.getWorld().getName() : "Unbekannt");
        placeholders.put("x", String.valueOf(to.getBlockX()));
        placeholders.put("y", String.valueOf(to.getBlockY()));
        placeholders.put("z", String.valueOf(to.getBlockZ()));

        String title = plugin.getLanguageManager().getMessage("teleport.title", placeholders);
        String content = plugin.getLanguageManager().getMessage("teleport.content", placeholders);

        DiscordWebhookSender.send(
                "teleport",
                playerName,
                title,
                content
        );
    }
}
