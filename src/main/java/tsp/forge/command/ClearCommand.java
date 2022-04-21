package tsp.forge.command;

import tsp.forge.Forge;

public class ClearCommand extends ForgeSubCommand {

    public ClearCommand() {
        super("clear", (player, item, args) -> {
            item.setItemMeta(null);
            Forge.getInstance().getLocalization().sendMessage(player, "metaClear", msg -> msg.replace("$name", item.getType().name()));
        });
    }

}
