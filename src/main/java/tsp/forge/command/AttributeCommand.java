package tsp.forge.command;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.forge.Forge;
import tsp.forge.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AttributeCommand extends ForgeSubCommand {

    public AttributeCommand() {
        super("attr", (player, item, args) -> {
            ItemMeta meta = item.getItemMeta();
            String sub = args[1];
            try {
                if (sub.equalsIgnoreCase("add") || sub.equalsIgnoreCase("a")) {
                    if (args.length < 5) {
                        Forge.getInstance().getLocalization().sendMessage(player, "attributeSubInvalid");
                        return;
                    }

                    meta.addAttributeModifier(Attribute.valueOf(args[2].toUpperCase()),
                            new AttributeModifier(
                                    UUID.randomUUID(),
                                    args[3],
                                    Integer.parseInt(args[4]),
                                    AttributeModifier.Operation.valueOf(args[5]),
                                    EquipmentSlot.valueOf(args[6])
                            )
                    );
                    Forge.getInstance().getLocalization().sendMessage(player, "attributeAdd", msg -> msg.replace("$name", args[2]));
                } else if (sub.equalsIgnoreCase("remove") || sub.equalsIgnoreCase("r")) {
                    if (args.length < 3) {
                        Forge.getInstance().getLocalization().sendMessage(player, "attributeInvalid");
                        return;
                    }

                    meta.removeAttributeModifier(Attribute.valueOf(args[2]));
                    Forge.getInstance().getLocalization().sendMessage(player, "attributeRemove", msg -> msg.replace("$name", args[2]));
                } else {
                    Forge.getInstance().getLocalization().sendMessage(player, "attributeSubInvalid");
                }
            } catch (IllegalArgumentException ex) {
                Forge.getInstance().getLocalization().sendMessage(player, "attributeInvalid");
            }

            item.setItemMeta(meta);
        });
    }

    private final List<String> attributes = Arrays.stream(Attribute.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length == 3) {
            return attributes;
        }

        return Utils.LIST_ADD_REMOVE;
    }

}
