package dev.eministar.logium.update;

import dev.eministar.logium.Logium;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    private final Logium plugin;
    private final String updateUrl = "https://star-plugins.com/logium-version.txt"; // <-- Hier holst du die Version ab
    private final String downloadUrl = "https://logium.star-plugins.com"; // <-- Hier downloadet man das

    public UpdateChecker(Logium plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(updateUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()));
                if (scanner.hasNextLine()) {
                    String latestVersion = scanner.nextLine().trim();
                    String currentVersion = plugin.getDescription().getVersion().trim();

                    if (!latestVersion.equalsIgnoreCase(currentVersion)) {
                        plugin.getLogger().warning("===========================================");
                        plugin.getLogger().warning("   Es ist ein Update für Logium verfügbar!");
                        plugin.getLogger().warning("   Aktuelle Version: " + currentVersion);
                        plugin.getLogger().warning("   Neueste Version:  " + latestVersion);
                        plugin.getLogger().warning("   Lade es herunter: " + downloadUrl);
                        plugin.getLogger().warning("===========================================");
                    } else {
                        plugin.getLogger().info("Logium ist auf dem neuesten Stand.");
                    }
                }
                scanner.close();
            } catch (Exception e) {
                plugin.getLogger().warning("Konnte nicht nach Updates suchen: " + e.getMessage());
            }
        });
    }
}
