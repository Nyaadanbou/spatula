package cc.mewcraft.spatula.command;

import cloud.commandframework.Command;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jetbrains.annotations.NotNull;

/**
 * This class makes it easier to prepare and register simple commands.
 */
@Singleton
public abstract class SimpleCommands {
    private final @NotNull CommandRegistry registry;

    @Inject
    public SimpleCommands(final @NotNull Plugin plugin) throws Exception {
        this.registry = new CommandRegistry(plugin);
    }

    /**
     * Gets the backed command registry.
     *
     * @return the backed command registry
     */
    public @NotNull CommandRegistry commandRegistry() {
        return registry;
    }

    /**
     * To implement this method, you may use {@link CommandRegistry#addCommand(Command)} to add commands
     * and {@link CommandRegistry#registerCommands()} to register all the added commands in this method.
     * <p>
     * This method is intended to be called upon plugin enabling to register commands with ease!
     * <p>
     * Example usage:
     * <pre>{@code
     *     public void registerCommands() {
     *         // Prepare commands
     *         commandRegistry().addCommand(commandRegistry()
     *             .commandBuilder("home")
     *             .senderType(Player.class)
     *             .handler(ctx -> {
     *                 // Code to teleport home
     *             }).build());
     *
     *         // Register commands
     *         commandRegistry().registerCommands();
     *     }
     * }</pre>
     */
    public abstract void registerCommands();
}
