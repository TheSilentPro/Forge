package tsp.forge.command;

import org.bukkit.inventory.meta.ItemMeta;
import tsp.forge.Forge;
import tsp.forge.util.Utils;
import tsp.smartplugin.utils.StringUtils;

public class NameCommand extends ForgeSubCommand {

    public NameCommand() {
        super("name", (player, item, args) -> {
            String name = Utils.translate(StringUtils.joinArgs(1, args));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
            Forge.getInstance().getLocalization().sendMessage(player, "name", msg -> msg.replace("$name", name));
        });
    }

}
