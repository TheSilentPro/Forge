package tsp.forge.util;

import org.bukkit.Bukkit;
import tsp.smartplugin.logger.AbstractLogger;
import tsp.smartplugin.utils.StringUtils;

public class Logger extends AbstractLogger {

    public Logger(boolean debug) {
        super("Forge", debug);
    }

    @Override
    public void log(LogLevel level, String message) {
        Bukkit.getConsoleSender().sendMessage(StringUtils.colorize("&7[&6&l" + getName() + "&7] " + level.getColor() + "[" + level.name() + "]: " + message + "\u001B[0m")); // Reset color after message
    }

}
