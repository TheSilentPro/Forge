package tsp.forge.command;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ForgeSubCommand {

    private final String name;
    private final String permission;
    private final CommandHandler handler;

    public ForgeSubCommand(String name, String permission, CommandHandler handler) {
        this.name = name;
        this.permission = permission;
        this.handler = handler;
    }

    public ForgeSubCommand(String name, CommandHandler handler) {
        this(name, name, handler);
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public CommandHandler getHandler() {
        return handler;
    }

    private final List<String> finalCompletions = new ArrayList<>();

    public List<String> getTabCompletions(Player player, String[] args) {
        return finalCompletions;
    }

    public int getMinimalArguments() {
        return 2;
    }

}
