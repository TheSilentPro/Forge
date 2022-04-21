package tsp.forge.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manager for all {@link ForgeSubCommand sub-commands}.
 *
 * @author TheSilentPro (Silent)
 */
public class CommandManager {

    private final Map<String, ForgeSubCommand> commands = new HashMap<>();

    /**
     * Register a {@link ForgeSubCommand}.
     *
     * @param command The command to register.
     */
    public void register(ForgeSubCommand command) {
        this.commands.put(command.getName(), command);
    }

    /**
     * Retrieve a {@link ForgeSubCommand} from the registry.
     *
     * @param name The name of the command
     * @return The command if found
     */
    public Optional<ForgeSubCommand> getCommand(String name) {
        return Optional.ofNullable(this.commands.get(name));
    }

    /**
     * Retrieve an unmodifiable {@link Map} of registered {@link ForgeSubCommand sub-commands}.
     *
     * @return An unmodifiable view of the registered commands.
     */
    public Map<String, ForgeSubCommand> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

    public void registerDefaults() {
        register(new HelpCommand());
        register(new NameCommand());
        register(new TypeCommand());
        register(new LoreCommand());
        register(new EnchantCommand());
        register(new FlagCommand());
        register(new BookCommand());
        register(new AttributeCommand());
        register(new PotionCommand());
        register(new HeadCommand());
        register(new ClearCommand());
    }

}
