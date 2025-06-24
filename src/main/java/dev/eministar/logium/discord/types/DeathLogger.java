package dev.eministar.logium.discord.types;

import dev.eministar.logium.Logium;
import dev.eministar.logium.discord.DiscordWebhookSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class DeathLogger {

    public static void log(Player player, String cause) {
        Logium plugin = Logium.getInstance();
        Location loc = player.getLocation();
        StringBuilder inventoryString = new StringBuilder();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                inventoryString.append("- ").append(item.getAmount())
                        .append("x ").append(item.getType().name()).append("\n");
            }
        }

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("world", loc.getWorld() != null ? loc.getWorld().getName() : "Unbekannt");
        placeholders.put("x", String.valueOf(loc.getBlockX()));
        placeholders.put("y", String.valueOf(loc.getBlockY()));
        placeholders.put("z", String.valueOf(loc.getBlockZ()));
        placeholders.put("cause", cause);
        placeholders.put("inventory", inventoryString.length() == 0 ? "_leer_" : inventoryString.toString());

        String title = plugin.getLanguageManager().getMessage("death.title", placeholders);
        String content = plugin.getLanguageManager().getMessage("death.content", placeholders);

        DiscordWebhookSender.send(
                "death",
                player.getName(),
                title,
                content
        );
    }
}
