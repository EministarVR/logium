package dev.eministar.logium.config;

import dev.eministar.logium.Logium;

public class ConfigManager {

    private final Logium plugin;

    public ConfigManager(Logium plugin) {
        this.plugin = plugin;
    }

    public boolean isEnabled(String key) {
        return plugin.getConfig().getBoolean("logs." + key + ".enabled", false);
    }

    public String getWebhook(String key) {
        return plugin.getConfig().getString("logs." + key + ".webhook", "");
    }
    public boolean isSimplified(String key) {
        return plugin.getConfig().getBoolean("logs." + key + ".simplified-message", false);
    }
}
