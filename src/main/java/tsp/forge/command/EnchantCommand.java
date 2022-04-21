package tsp.forge.command;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import tsp.forge.Forge;
import tsp.forge.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantCommand extends ForgeSubCommand {

    public EnchantCommand() {
        super("enchant", (player, item, args) -> {
            String sub = args[1];
            if (args.length < 3) {
                Forge.getInstance().getLocalization().sendMessage(player, "enchantSubInvalid");
                return;
            }

            String name = args[2].toLowerCase();
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(name));
            if (enchantment == null) {
                Forge.getInstance().getLocalization().sendMessage(player, "enchantInvalid", msg -> msg.replace("$name", name));
                return;
            }

            if (sub.equalsIgnoreCase("add") || sub.equalsIgnoreCase("a")) {
                int level = 1;
                try {
                    level = Integer.parseInt(args[3]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
                item.addUnsafeEnchantment(enchantment, level);
                Forge.getInstance().getLocalization().sendMessage(player, "enchantAdd", msg -> msg.replace("$name", name));
            } else if (sub.equalsIgnoreCase("remove") || sub.equalsIgnoreCase("r")) {
                item.removeEnchantment(enchantment);
                Forge.getInstance().getLocalization().sendMessage(player, "enchantRemove", msg -> msg.replace("$name", name));
            } else {
                Forge.getInstance().getLocalization().sendMessage(player, "enchantSubInvalid");
            }
        });
    }

    private final List<String> enchantments = Arrays.stream(Enchantment.values())
            .map(enchantment -> enchantment.getKey().getKey())
            .collect(Collectors.toList());

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length == 3) {
            return enchantments;
        }

        return Utils.LIST_ADD_REMOVE;
    }

}
