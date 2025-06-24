package dev.eministar.logium.listener;

import dev.eministar.logium.discord.types.MuteLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MuteListener implements Listener {
    @EventHandler
    public void onMuteCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();
        if (msg.startsWith("/mute")) {
            String[] args = event.getMessage().split(" ", 3);
            if (args.length >= 2) {
                String target = args[1];
                String reason = (args.length >= 3) ? args[2] : "Kein Grund angegeben.";
                MuteLogger.log(target, reason);
            }
        }
    }
}
