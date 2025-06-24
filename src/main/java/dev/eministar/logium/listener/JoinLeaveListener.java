package dev.eministar.logium.listener;

import dev.eministar.logium.discord.types.JoinLeaveLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        JoinLeaveLogger.logJoin(event.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        JoinLeaveLogger.logLeave(event.getPlayer().getName());
    }
}
