package tsp.forge.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.forge.Forge;
import tsp.forge.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlagCommand extends ForgeSubCommand {

    public FlagCommand() {
        super("flag", (player, item, args) -> {
            String sub = args[1];
            ItemMeta meta = item.getItemMeta();
            List<ItemFlag> flags = new ArrayList<>();
            if (args.length < 3) {
                Forge.getInstance().getLocalization().sendMessage(player, "flagSubInvalid");
                return;
            }

            for (int i = 2; i < args.length; i++) {
                try {
                    flags.add(ItemFlag.valueOf(args[i].toUpperCase()));
                } catch (IllegalArgumentException ignored) {}
            }

            if (flags.isEmpty()) {
                Forge.getInstance().getLocalization().sendMessage(player, "flagInvalid");
                return;
            }

            if (sub.equalsIgnoreCase("add") || sub.equalsIgnoreCase("a")) {
                meta.addItemFlags(flags.toArray(new ItemFlag[0]));
                Forge.getInstance().getLocalization().sendMessage(player, "flagAdd", msg -> msg.replace("$name", Arrays.toString(flags.toArray())));
            } else if (sub.equalsIgnoreCase("remove") || sub.equalsIgnoreCase("r")) {
                meta.removeItemFlags(flags.toArray(new ItemFlag[0]));
                Forge.getInstance().getLocalization().sendMessage(player, "flagRemove", msg -> msg.replace("$name", Arrays.toString(flags.toArray())));
            } else {
                Forge.getInstance().getLocalization().sendMessage(player, "flagSubInvalid");
            }

            item.setItemMeta(meta);
        });
    }

    private final List<String> flags = Arrays.stream(ItemFlag.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length >= 3) {
            return flags;
        }

        return Utils.LIST_ADD_REMOVE;
    }

}
