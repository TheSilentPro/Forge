package tsp.forge.command;

import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import tsp.forge.Forge;
import tsp.smartplugin.utils.Localization;

public class LevelCommand extends ForgeSubCommand {

    public LevelCommand() {
        super("level", (player, item, args) -> {
            Localization localization = Forge.getInstance().getLocalization();
            try {
                Block block = player.getTargetBlockExact(5, FluidCollisionMode.ALWAYS);
                if (block == null) {
                    localization.sendMessage(player, "levelInvalid");
                    return;
                }

                BlockData data = block.getBlockData();
                if (!(data instanceof Levelled)) {
                    localization.sendMessage(player, "levelInvalid");
                    return;
                }
                Levelled levelled = (Levelled) data;

                int level = Integer.parseInt(args[1]);
                if (level > levelled.getMaximumLevel()) {
                    localization.sendMessage(player, "levelMax", msg -> msg.replace("$name", String.valueOf(levelled.getMaximumLevel())));
                    return;
                }

                /*
                 * Definition:
                 * 'level' represents the amount of fluid contained within this block, either by itself or inside a cauldron.
                 * In the case of water and lava blocks the levels have special meanings: a level of 0 corresponds to a source block, 1-7 regular fluid heights, and 8-15 to "falling" fluids. All falling fluids have the same behaviour, but the level corresponds to that of the block above them, equal to this.level - 8 Note that counterintuitively, an adjusted level of 1 is the highest level, whilst 7 is the lowest.
                 * May not be higher than getMaximumLevel().
                 */
                levelled.setLevel(level);

                BlockState state = block.getState();
                state.setBlockData(levelled);
                state.update(false, false);
                localization.sendMessage(player, "levelSet", msg -> msg.replace("$name", String.valueOf(level)));
            } catch (NumberFormatException nfe) {
                localization.sendMessage(player, "levelSubInvalid");
            }
        });
    }

    @Override
    public boolean isRequireItem() {
        return false;
    }

}
