package dev.eministar.logium.config;

import dev.eministar.logium.Logium;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LanguageManager {

    private final Logium plugin;
    private YamlConfiguration langConfig;

    public LanguageManager(Logium plugin) {
        this.plugin = plugin;
        loadLanguage();
    }

    private void loadLanguage() {
        String lang = plugin.getConfig().getString("language", "de").toLowerCase();
        File langFolder = new File(plugin.getDataFolder(), "lang");

        if (!langFolder.exists()) {
            langFolder.mkdirs();
            plugin.saveResource("lang/lang_de.yml", false);
            plugin.saveResource("lang/lang_en.yml", false);
        }

        File langFile = new File(langFolder, "lang_" + lang + ".yml");

        if (!langFile.exists()) {
            plugin.getLogger().warning("[Logium] Sprachdatei 'lang_" + lang + ".yml' nicht gefunden. Erstelle leere Vorlage...");
            createEmptyLanguageFile(langFile, lang);
            plugin.getLogger().warning("[Logium] Bitte fülle die Sprachdatei aus: plugins/Logium/lang/lang_" + lang + ".yml");
            lang = "de";
            langFile = new File(langFolder, "lang/lang_de.yml");
        }

        this.langConfig = YamlConfiguration.loadConfiguration(langFile);


        try {
            Reader defConfigStream = new InputStreamReader(plugin.getResource("lang/lang_de.yml"), StandardCharsets.UTF_8);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            this.langConfig.setDefaults(defConfig);
        } catch (Exception e) {
            plugin.getLogger().warning("[Logium] Konnte Default-Sprache nicht laden: " + e.getMessage());
        }
    }

    private void createEmptyLanguageFile(File file, String lang) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("# Sprachdatei für '" + lang + "'\n");
            writer.write("# Füge hier deine Übersetzungen ein.\n");
        } catch (Exception e) {
            plugin.getLogger().warning("[Logium] Fehler beim Erstellen von Sprachdatei: " + e.getMessage());
        }
    }

    public String getMessage(String path) {
        String message = langConfig.getString(path);
        if (message == null) {
            return "§c[Logium] Missing text: " + path;
        }
        return message.replace("&", "§");
    }

    public String getMessage(String path, Map<String, String> placeholders) {
        String message = getMessage(path);
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace("%" + entry.getKey() + "%", entry.getValue());
        }
        return message;
    }

    public void reloadLanguage() {
        loadLanguage();
    }
}
