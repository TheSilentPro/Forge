package tsp.forge.command;

import org.bukkit.inventory.meta.ItemMeta;
import tsp.forge.Forge;
import tsp.smartplugin.utils.StringUtils;

public class NameCommand extends ForgeSubCommand {

    public NameCommand() {
        super("name", (player, item, args) -> {
            String name = StringUtils.colorize(StringUtils.joinArgs(1, args));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
            Forge.getInstance().getLocalization().sendMessage(player, "name", msg -> msg.replace("$name", name));
        });
    }

}
