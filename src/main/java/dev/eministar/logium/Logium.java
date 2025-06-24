package dev.eministar.logium;

import dev.eministar.logium.commands.LogiumCommand;
import dev.eministar.logium.commands.LogiumCommandTabCompleter;
import dev.eministar.logium.commands.LogiumDebugCommand;
import dev.eministar.logium.config.ConfigManager;
import dev.eministar.logium.config.LanguageManager;
import dev.eministar.logium.listener.*;
import dev.eministar.logium.log.ErrorLogger;
import dev.eministar.logium.update.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public class Logium extends JavaPlugin {

    private static Logium instance;
    private ConfigManager configManager;
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        languageManager = new LanguageManager(this);


        new UpdateChecker(this).checkForUpdate();

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            ErrorLogger.logError(getStackTrace(throwable));
        });

        getCommand("logium").setExecutor(new LogiumCommand());
        getCommand("logium").setTabCompleter(new LogiumCommandTabCompleter());
        getCommand("logiumdebug").setExecutor(new LogiumDebugCommand());

        if (configManager.isEnabled("chat")) getServer().getPluginManager().registerEvents(new ChatListener(), this);
        if (configManager.isEnabled("join-leave")) getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        if (configManager.isEnabled("teleport")) getServer().getPluginManager().registerEvents(new TeleportListener(), this);
        if (configManager.isEnabled("ban")) getServer().getPluginManager().registerEvents(new BanListener(), this);
        if (configManager.isEnabled("mute")) getServer().getPluginManager().registerEvents(new MuteListener(), this);
        if (configManager.isEnabled("death")) getServer().getPluginManager().registerEvents(new DeathListener(), this);

        printStartupArt();
    }

    @Override
    public void onDisable() {
        getLogger().info("Logium wurde deaktiviert.");
    }

    public static Logium getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public void setLanguageManager(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder result = new StringBuilder();
        result.append(throwable.toString()).append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            result.append("    at ").append(element.toString()).append("\n");
        }
        return result.toString();
    }


    private void printStartupArt() {
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_RESET = "\u001B[0m";

        System.out.println(ANSI_PURPLE +
                "                                                                 \n" +
                "                                                                 \n" +
                "                                                                 \n" +
                "                                                                 \n" +
                "                                                                 \n" +
                "                                                                 \n" +
                "                                                                 \n" +
                "                                                   @             \n" +
                "                                               #####             \n" +
                "              %%%%            %##%%         %#######             \n" +
                "             %####         ##########%    %#########             \n" +
                "             %####       %############%  #####%####%             \n" +
                "             %####      %####%     %%   ####%%#####              \n" +
                "             %####      #####          %###%%####%               \n" +
                "             %####      #####          ###%%#####                \n" +
                "             %####      #####  @###### ###%#####                 \n" +
                "             %####      #####   ###### ## ####%                  \n" +
                "             %####      #####     #### ##%##%                    \n" +
                "             %####      %####     #### #%         %              \n" +
                "             %####@@@@@  #####%  %####@#         @#%             \n" +
                "             %#########%  ########### %# %###### @#%             \n" +
                "             %#########%     %####%              @#%             \n" +
                "                                    ############ @#%             \n" +
                "                             @##                 @#%             \n" +
                "                             @##  ############## @#%             \n" +
                "                             @##                 @#%             \n" +
                "                              #####%##%##%%%#%%%###%             \n" +
                "                                %%%%%%%%%%%%%%%%%                \n" +
                "                                                                 \n" +
                "                                                                 \n" +
                "                                                                 \n" +
                "╔══════════════════════════════════════╗\n" +
                "║   " + Logium.getInstance().getLanguageManager().getMessage("console.startup.header") + "   ║\n" +
                "║     " + Logium.getInstance().getLanguageManager().getMessage("console.startup.footer") + "    ║\n" +
                "╚══════════════════════════════════════╝" +
                ANSI_RESET);
    }



}
