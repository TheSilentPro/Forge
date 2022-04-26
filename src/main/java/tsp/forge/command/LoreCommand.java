package tsp.forge.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.forge.Forge;
import tsp.forge.util.Utils;
import tsp.smartplugin.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand extends ForgeSubCommand {

    public LoreCommand() {
        super("lore", (player, item, args) -> {
            List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<>();
            String sub = args[1];
            if (args.length < 3) {
                Forge.getInstance().getLocalization().sendMessage(player, "loreSubInvalid");
                return;
            }

            String loreArgs = StringUtils.joinArgs(2, args);
            if (sub.equalsIgnoreCase("add") || sub.equalsIgnoreCase("a")) {
                String line = Utils.translate(loreArgs);
                lore.add(line);
                item.getItemMeta().setLore(lore);
                Forge.getInstance().getLocalization().sendMessage(player, "loreAdd", msg -> msg
                        .replace("$lore", line)
                        .replace("$index", String.valueOf(lore.size()))
                );
            } else if (sub.equalsIgnoreCase("remove") || sub.equalsIgnoreCase("r")) {
                try {
                    int index = Integer.parseInt(loreArgs);
                    String line = lore.get(index - 1);
                    lore.remove(index);
                    Forge.getInstance().getLocalization().sendMessage(player, "loreRemove", msg -> msg
                            .replace("$lore", line)
                            .replace("$index", String.valueOf(index))
                    );
                } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                    Forge.getInstance().getLocalization().sendMessage(player, "invalidNumber", msg -> msg
                            .replace("$index", loreArgs));
                }
            } else {
                Forge.getInstance().getLocalization().sendMessage(player, "loreSubInvalid");
            }

            ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            item.setItemMeta(meta);
        });
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        return Utils.LIST_ADD_REMOVE;
    }

}
