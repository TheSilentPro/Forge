package tsp.forge.command;

import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import tsp.forge.Forge;
import tsp.smartplugin.utils.Localization;

public class WaterloggedCommand extends ForgeSubCommand {

    public WaterloggedCommand() {
        super("waterlog", (player, item, args) -> {
            Localization localization = Forge.getInstance().getLocalization();
            Block block = player.getTargetBlockExact(5, FluidCollisionMode.NEVER);
            if (block == null) {
                localization.sendMessage(player, "waterInvalid");
                return;
            }

            BlockData data = block.getBlockData();
            if (!(data instanceof Waterlogged)) {
                localization.sendMessage(player, "waterInvalid");
                return;
            }
            Waterlogged wl = (Waterlogged) data;

            wl.setWaterlogged(!wl.isWaterlogged());

            BlockState state = block.getState();
            state.setBlockData(wl);
            state.update(false, false);
            localization.sendMessage(player, "waterSet", msg -> msg.replace("$name", String.valueOf(wl.isWaterlogged())));
        });
    }

    @Override
    public int getMinimalArguments() {
        return 0;
    }

    @Override
    public boolean isRequireItem() {
        return false;
    }

}
