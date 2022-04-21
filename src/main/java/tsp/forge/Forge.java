package tsp.forge;

import tsp.forge.command.CommandManager;
import tsp.forge.command.ForgeCommand;
import tsp.forge.util.Logger;
import tsp.smartplugin.SmartPlugin;
import tsp.smartplugin.utils.Localization;

public class Forge extends SmartPlugin {

    private static Forge instance;
    private Logger logger;
    private Localization localization;
    private CommandManager commandManager;

    @Override
    public void onStart() {
        instance = this;
        saveDefaultConfig();
        this.logger = new Logger(getConfig().getBoolean("debug"));
        logger.info("Loading Forge - " + getDescription().getVersion());
        this.localization = new Localization(this);
        this.localization.load();
        this.commandManager = new CommandManager();
        commandManager.registerDefaults();
        new ForgeCommand().register(this);

        logger.info("Done!");
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Localization getLocalization() {
        return localization;
    }

    public Logger getLog() {
        return logger;
    }

    public static Forge getInstance() {
        return instance;
    }

}
