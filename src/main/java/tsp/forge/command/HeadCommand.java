package tsp.forge.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tsp.forge.Forge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HeadCommand extends ForgeSubCommand {

    @SuppressWarnings("deprecated")
    public HeadCommand() {
        super("head", (player, item, args) -> {
            ItemMeta meta = item.getItemMeta();
            if (!(meta instanceof SkullMeta)) {
                Forge.getInstance().getLocalization().sendMessage(player, "headInvalid");
                return;
            }
            ((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(args[1]));
            // TODO: UUID Version

            item.setItemMeta(meta);
            Forge.getInstance().getLocalization().sendMessage(player, "headSet", msg -> msg.replace("$name", args[1]));
        });
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                .map(OfflinePlayer::getName)
                .collect(Collectors.toList()); // Players can join at any time, has to be fetched every time
    }

}
