package dev.eministar.logium.commands;

import dev.eministar.logium.Logium;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.lang.management.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class LogiumDebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("logium.debug")) {
            sender.sendMessage("§cDu hast keine Berechtigung, diesen Befehl auszuführen. (§elogium.debug§c)");
            return true;
        }

        try {
            File file = new File(Logium.getInstance().getDataFolder(), "info.logium");
            FileWriter writer = new FileWriter(file);

            Server server = Bukkit.getServer();
            Logium plugin = Logium.getInstance();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            Runtime runtime = Runtime.getRuntime();
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

            writer.write("═══════════════════════════════════════════════════════\n");
            writer.write("               LOGIUM DEBUG-DATEI\n");
            writer.write("═══════════════════════════════════════════════════════\n");
            writer.write("Zeitpunkt:              " + timestamp + "\n");
            writer.write("Plugin-Version:         " + plugin.getDescription().getVersion() + "\n");
            writer.write("Sprache:                " + plugin.getConfig().getString("lang", "de") + "\n");
            writer.write("Server-Name:            " + server.getName() + "\n");
            writer.write("\n");

            writer.write("═════════════ SERVER-INFORMATIONEN ════════════════════\n");
            writer.write("Bukkit-Version:         " + server.getBukkitVersion() + "\n");
            writer.write("Minecraft-Version:      " + server.getVersion() + "\n");
            writer.write("IP:                     " + server.getIp() + "\n");
            writer.write("Port:                   " + server.getPort() + "\n");
            writer.write("Online-Modus:           " + server.getOnlineMode() + "\n");
            writer.write("Max-Players:            " + server.getMaxPlayers() + "\n");
            writer.write("Aktuelle Spieler:       " + server.getOnlinePlayers().size() + "\n");
            writer.write("\n");

            writer.write("═════════════ SYSTEM-INFORMATIONEN ════════════════════\n");
            writer.write("Betriebssystem:         " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n");
            writer.write("Architektur:            " + System.getProperty("os.arch") + "\n");
            writer.write("Kerne (logisch):        " + osBean.getAvailableProcessors() + "\n");
            writer.write("RAM gesamt (MB):        " + (runtime.totalMemory() / 1024 / 1024) + "\n");
            writer.write("RAM frei (MB):          " + (runtime.freeMemory() / 1024 / 1024) + "\n");
            writer.write("RAM max (MB):           " + (runtime.maxMemory() / 1024 / 1024) + "\n");
            writer.write("Uptime (Minuten):       " + (runtimeMXBean.getUptime() / 1000 / 60) + "\n");
            writer.write("JVM Arguments:          " + String.join(" ", runtimeMXBean.getInputArguments()) + "\n");
            writer.write("Host-Name:              " + InetAddress.getLocalHost().getHostName() + "\n");
            writer.write("Host-IP:                " + InetAddress.getLocalHost().getHostAddress() + "\n");
            writer.write("\n");

            writer.write("═════════════ SPEICHERINFORMATIONEN ═══════════════════\n");
            writer.write("Heap Usage:             " + memoryBean.getHeapMemoryUsage().toString() + "\n");
            writer.write("Non-Heap Usage:         " + memoryBean.getNonHeapMemoryUsage().toString() + "\n");
            writer.write("\n");

            writer.write("═════════════ THREAD-INFORMATIONEN ════════════════════\n");
            writer.write("Aktive Threads:         " + threadBean.getThreadCount() + "\n");
            writer.write("Daemon Threads:         " + threadBean.getDaemonThreadCount() + "\n");
            writer.write("Peak Threads:           " + threadBean.getPeakThreadCount() + "\n");
            writer.write("Total Started Threads:  " + threadBean.getTotalStartedThreadCount() + "\n");
            writer.write("\n");

            writer.write("═════════════ GELADENE PLUGINS ════════════════════════\n");
            for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                writer.write("- " + p.getName() + " v" + p.getDescription().getVersion() +
                        (p.isEnabled() ? " [aktiv]" : " [deaktiviert]") + "\n");
            }
            writer.write("\n");

            writer.write("═════════════ AKTUELLE CONFIGURATION ══════════════════\n");
            for (String key : plugin.getConfig().getKeys(true)) {
                Object value = plugin.getConfig().get(key);
                writer.write(key + ": " + (value == null ? "null" : value.toString()) + "\n");
            }
            writer.write("\n");

            writer.write("═════════════ ENVIRONMENT-VARIABLEN ═══════════════════\n");
            for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
                writer.write(entry.getKey() + " = " + entry.getValue() + "\n");
            }
            writer.write("\n");

            writer.write("═══════════════════════════════════════════════════════\n");
            writer.write("    Datei automatisch generiert von Logium Debug Tool\n");
            writer.write("    Falls du Hilfe brauchst → melde dich beim Support\n");
            writer.write("═══════════════════════════════════════════════════════\n");

            writer.close();
            sender.sendMessage("§8[§dLogium§8] §aInfo-Datei 'info.logium' wurde erfolgreich erstellt.");
        } catch (Exception e) {
            sender.sendMessage("§8[§dLogium§8] §cFehler beim Erstellen der Debug-Datei.");
            e.printStackTrace();
        }
        return true;
    }

}
