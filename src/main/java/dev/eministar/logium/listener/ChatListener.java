package dev.eministar.logium.listener;

import dev.eministar.logium.discord.types.ChatLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        ChatLogger.log(event.getPlayer().getName(), event.getMessage());
    }
}
