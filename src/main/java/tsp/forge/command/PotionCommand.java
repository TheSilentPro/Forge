package tsp.forge.command;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tsp.forge.Forge;
import tsp.forge.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PotionCommand extends ForgeSubCommand {

    public PotionCommand() {
        super("potion", (player, item, args) -> {
            ItemMeta meta = item.getItemMeta();
            if (!(meta instanceof PotionMeta)) {
                Forge.getInstance().getLocalization().sendMessage(player, "potionInvalid");
                return;
            }
            String sub = args[1];
            if (sub.equalsIgnoreCase("color") || sub.equalsIgnoreCase("c")) {
                Optional<Color> color = Utils.match(args[2]);
                ((PotionMeta) meta).setColor(color.orElse(Color.RED));
                Forge.getInstance().getLocalization().sendMessage(player, "potionColorAdd", msg -> msg.replace("$name", args[2]));
            } else if (sub.equalsIgnoreCase("base") || sub.equalsIgnoreCase("b")) {
                // TODO: Implement, might require reflection to fetch PotionType
            } else if (sub.equalsIgnoreCase("add") || sub.equalsIgnoreCase("a")) {
                if (args.length < 4) {
                    Forge.getInstance().getLocalization().sendMessage(player, "potionSubInvalid");
                    return;
                }
                PotionEffectType effect = PotionEffectType.getByName(args[2]); // method already transfers to lowercase
                try {
                    ((PotionMeta) meta).addCustomEffect(new PotionEffect(
                            effect != null ? effect : PotionEffectType.INVISIBILITY,
                            Integer.parseInt(args[3]) * 20,
                            Integer.parseInt(args[4]) - 1
                    ), true);
                    Forge.getInstance().getLocalization().sendMessage(player, "potionAdd", msg -> msg.replace("$name", effect.getName()));
                } catch (NumberFormatException nfe) {
                    Forge.getInstance().getLocalization().sendMessage(player, "invalidNumber");
                }
            } else if (sub.equalsIgnoreCase("remove") || sub.equalsIgnoreCase("r")) {
                PotionEffectType effect = PotionEffectType.getByName(args[2]); // method already transfers to lowercase
                ((PotionMeta) meta).removeCustomEffect(effect != null ? effect : PotionEffectType.INVISIBILITY);
                Forge.getInstance().getLocalization().sendMessage(player, "potionRemove", msg -> msg.replace("$name", effect.getName()));
            } else {
                Forge.getInstance().getLocalization().sendMessage(player, "potionSubInvalid");
            }

            item.setItemMeta(meta);
        });
    }

    private final List<String> effects = Arrays.stream(PotionEffectType.values())
            .map(PotionEffectType::getName)
            .collect(Collectors.toList());

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length == 3) {
            if (args[2].equalsIgnoreCase("color") || args[2].equalsIgnoreCase("c")) {
                return Utils.getColorsAsStrings();
            } else {
                return effects;
            }
        }

        return Utils.LIST_COLOR;
    }

}
