package tsp.forge.command;

import tsp.forge.Forge;

public class HelpCommand extends ForgeSubCommand {

    public HelpCommand() {
        super("help", (player, item, args) -> {
            Forge.getInstance().getLocalization().sendMessage(player, "help");
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
