package tsp.forge.command;

import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import tsp.forge.Forge;
import tsp.forge.util.Utils;
import tsp.smartplugin.utils.Localization;
import tsp.smartplugin.utils.StringUtils;

public class SignCommand extends ForgeSubCommand {

    public SignCommand() {
        super("sign", (player, item, args) -> {
            Localization localization = Forge.getInstance().getLocalization();
            String sub = args[1];
            Block block = player.getTargetBlockExact(5, FluidCollisionMode.NEVER);
            if (block == null) {
                localization.sendMessage(player, "signInvalid");
                return;
            }

            BlockState state = block.getState();
            if (!(state instanceof Sign)) {
                localization.sendMessage(player, "signInvalid");
                return;
            }
            Sign sign = (Sign) state;

            if (sub.equalsIgnoreCase("glow") || sub.equalsIgnoreCase("g")) {
                sign.setGlowingText(!sign.isGlowingText());
                sign.update(false, false);
                localization.sendMessage(player, "signGlow", msg -> msg.replace("$name", String.valueOf(sign.isGlowingText())));
            } else if (sub.equalsIgnoreCase("set") || sub.equalsIgnoreCase("s")) {
                if (args.length < 3) {
                    localization.sendMessage(player, "signSubInvalid");
                    return;
                }

                try {
                    int index = Integer.parseInt(args[2]);
                    String text = Utils.translate(StringUtils.joinArgs(3, args));
                    sign.setLine(index, text);
                    sign.update(false, false);
                    localization.sendMessage(player, "signSet", msg -> msg
                            .replace("$index", args[2])
                            .replace("$name", text)
                    );
                } catch (NumberFormatException nfe) {
                    localization.sendMessage(player, "invalidNumber", msg -> msg.replace("$index", args[2]));
                }
            } else {
                localization.sendMessage(player, "signSubInvalid");
            }
        });
    }

    @Override
    public boolean isRequireItem() {
        return false;
    }

}
