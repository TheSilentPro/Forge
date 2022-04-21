package tsp.forge.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import tsp.forge.Forge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TypeCommand extends ForgeSubCommand {

    public TypeCommand() {
        super("type", (player, item, args) -> {
            Material material = Material.matchMaterial(args[1]);
            if (material == null) {
                Forge.getInstance().getLocalization().sendMessage(player, "typeInvalid", msg -> msg.replace("$type", args[1]));
                return;
            }

            item.setType(material);
            Forge.getInstance().getLocalization().sendMessage(player, "type", msg -> msg.replace("$type", material.name()));
        });
    }

    private final List<String> materials = Arrays.stream(Material.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        return materials;
    }

}
