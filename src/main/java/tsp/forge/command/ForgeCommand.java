package tsp.forge.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tsp.forge.Forge;
import tsp.smartplugin.player.PlayerUtils;
import tsp.smartplugin.utils.Localization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ForgeCommand implements CommandExecutor, TabCompleter {

    private final Localization localization = Forge.getInstance().getLocalization();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            PlayerUtils.sendMessage(sender, "&7Running &6Forge - " + Forge.getInstance().getDescription().getVersion());
            PlayerUtils.sendMessage(sender, "&7Created by &6" + Forge.getInstance().getDescription().getAuthors());
            PlayerUtils.sendMessage(sender, "&7Run &6/f help &7for more information.");
            return true;
        }

        if (!(sender instanceof Player)) {
            localization.sendMessage(sender, "onlyPlayers");
            return true;
        }
        Player player = (Player) sender;

        String sub = args[0];
        CommandManager manager = Forge.getInstance().getCommandManager();
        Optional<ForgeSubCommand> optional = manager.getCommand(sub);
        if (!optional.isPresent()) {
            localization.sendMessage(player, "invalidArguments");
            return true;
        }
        ForgeSubCommand cmd = optional.get();

        if (!player.hasPermission("forge.command." + cmd.getPermission())) {
            localization.sendMessage(player, "noPermission");
            return true;
        }

        if (args.length < cmd.getMinimalArguments()) {
            localization.sendMessage(player, "invalidArguments");
            return true;
        }

        if (player.getInventory().getItemInMainHand().getType().isAir() && cmd.isRequireItem()) { // Ignore commands that don't require an item
            localization.sendMessage(player, "invalidItem");
            return true;
        }

        cmd.getHandler().handle(player, player.getInventory().getItemInMainHand(), args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList(); // All commands expect a player
        }

        List<String> result;
        if (args.length == 1) {
            result = new ArrayList<>(Forge.getInstance().getCommandManager().getCommands().keySet());
        } else {
            String sub = args[0];
            Optional<ForgeSubCommand> cmd = Forge.getInstance().getCommandManager().getCommand(sub);
            if (cmd.isPresent()) {
                result = cmd.get().getTabCompletions((Player) sender, args);
            } else {
                result = new ArrayList<>();
            }
        }

        return result;
    }

    public void register(Forge plugin) {
        PluginCommand command = plugin.getCommand("forge");
        if (command == null) {
            plugin.getLog().error("Failed to register 'forge' command!");
            return;
        }

        command.setExecutor(this);
        command.setTabCompleter(this);
    }

}
