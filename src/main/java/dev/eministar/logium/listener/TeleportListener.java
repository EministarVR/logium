package dev.eministar.logium.listener;

import dev.eministar.logium.discord.types.TeleportLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getTo() != null)
            TeleportLogger.logTeleport(event.getPlayer().getName(), event.getTo());
    }
}
