package dev.eministar.logium.commands;

import dev.eministar.logium.Logium;
import dev.eministar.logium.config.ConfigManager;
import dev.eministar.logium.config.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogiumCommand implements CommandExecutor {

    private static final List<String> SUPPORTED_TYPES = Arrays.asList(
            "chat", "join-leave", "teleport", "ban", "mute", "msg"
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Logium plugin = Logium.getInstance();


        if (!sender.hasPermission("logium.command")) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("prefix") + plugin.getLanguageManager().getMessage("no-permission"));
            return true;
        }


        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.setConfigManager(new ConfigManager(plugin));
            plugin.setLanguageManager(new LanguageManager(plugin));
            sender.sendMessage(plugin.getLanguageManager().getMessage("prefix") + plugin.getLanguageManager().getMessage("config-reloaded"));
            return true;
        }


        if (args.length == 1 && SUPPORTED_TYPES.contains(args[0].toLowerCase())) {
            String key = args[0].toLowerCase();
            String url = plugin.getConfig().getString("logs." + key + ".webhook", "§cNicht gesetzt.");

            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("type", key);
            placeholders.put("url", url);

            sender.sendMessage(plugin.getLanguageManager().getMessage("prefix") + plugin.getLanguageManager().getMessage("current-webhook", placeholders));
            return true;
        }


        if (args.length == 2 && SUPPORTED_TYPES.contains(args[0].toLowerCase())) {
            String key = args[0].toLowerCase();
            String url = args[1];

            plugin.getConfig().set("logs." + key + ".webhook", url);
            plugin.saveConfig();

            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("type", key);

            sender.sendMessage(plugin.getLanguageManager().getMessage("prefix") + plugin.getLanguageManager().getMessage("webhook-set", placeholders));
            return true;
        }


        sender.sendMessage(plugin.getLanguageManager().getMessage("prefix") + plugin.getLanguageManager().getMessage("usage-header"));
        sender.sendMessage(plugin.getLanguageManager().getMessage("usage-reload"));
        sender.sendMessage(plugin.getLanguageManager().getMessage("usage-setwebhook"));
        return true;
    }
}
