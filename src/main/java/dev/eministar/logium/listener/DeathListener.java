package dev.eministar.logium.listener;

import dev.eministar.logium.discord.types.DeathLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = event.getEntity();
        String cause = event.getDeathMessage(); // Nice Minecraft-Death-Text

        DeathLogger.log(player, cause != null ? cause : "Unbekannt");
    }
}
