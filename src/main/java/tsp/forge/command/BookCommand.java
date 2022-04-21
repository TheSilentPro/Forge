package tsp.forge.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.forge.Forge;
import tsp.forge.util.Utils;
import tsp.smartplugin.utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookCommand extends ForgeSubCommand {

    public BookCommand() {
        super("book", (player, item, args) -> {
            ItemMeta meta = item.getItemMeta();
            if (!(meta instanceof BookMeta)) {
                Forge.getInstance().getLocalization().sendMessage(player, "bookInvalid");
                return;
            }

            String sub = args[1];
            if (args.length < 3) {
                Forge.getInstance().getLocalization().sendMessage(player, "bookSubInvalid");
                return;
            }

            String text = StringUtils.colorize(StringUtils.joinArgs(2, args));
            if (sub.equalsIgnoreCase("title") || sub.equalsIgnoreCase("t")) {
                ((BookMeta) meta).setTitle(text);
                Forge.getInstance().getLocalization().sendMessage(player, "bookTitle", msg -> msg.replace("$name", text));
            } else if (sub.equalsIgnoreCase("author") || sub.equalsIgnoreCase("a")) {
                ((BookMeta) meta).setAuthor(text);
                Forge.getInstance().getLocalization().sendMessage(player, "bookAuthor", msg -> msg.replace("$name", text));
            } else if (sub.equalsIgnoreCase("generation") || sub.equalsIgnoreCase("gen") || sub.equalsIgnoreCase("g")) {
                try {
                    ((BookMeta) meta).setGeneration(BookMeta.Generation.valueOf(text.toUpperCase()));
                    Forge.getInstance().getLocalization().sendMessage(player, "bookGeneration", msg -> msg.replace("$name", text));
                } catch (IllegalArgumentException ex) {
                    Forge.getInstance().getLocalization().sendMessage(player, "bookGenerationInvalid");
                }
            } else {
                Forge.getInstance().getLocalization().sendMessage(player, "bookSubInvalid");
            }

            item.setItemMeta(meta);
        });
    }

    private final List<String> generations = Arrays.stream(BookMeta.Generation.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length == 4) {
            if (args[3].equalsIgnoreCase("generation") || args[3].equalsIgnoreCase("gen") || args[3].equalsIgnoreCase("g")) {
                return generations;
            }
        }

        return Utils.LIST_BOOK;
    }

}
