package tsp.forge.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Handler for a {@link ForgeSubCommand}.
 */
public interface CommandHandler {

    /**
     * Handle the sub-command.
     *
     * @param player The player that executed this command
     * @param item The item in the player's hand
     * @param args The arguments of the command
     */
    void handle(Player player, ItemStack item, String[] args);

}
