package cc.mewcraft.spatula.command;

import cloud.commandframework.Command;
import cloud.commandframework.brigadier.CloudBrigadierManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.minecraft.extras.AudienceProvider;
import cloud.commandframework.minecraft.extras.MinecraftExceptionHandler;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jetbrains.annotations.NotNull;

@Singleton
public class CommandRegistry extends PaperCommandManager<CommandSender> {
    /**
     * Commands to be registered in the tree.
     */
    private final List<Command<CommandSender>> storedCommands;

    @Inject
    public CommandRegistry(@NotNull Plugin plugin) throws Exception {
        super(
                plugin,
                CommandExecutionCoordinator.simpleCoordinator(),
                UnaryOperator.identity(),
                UnaryOperator.identity()
        );

        this.storedCommands = new ArrayList<>();

        // Register Brigadier
        if (hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            registerBrigadier();
            CloudBrigadierManager<CommandSender, ?> brigManager = brigadierManager();
            if (brigManager != null) brigManager.setNativeNumberSuggestions(false);
            plugin.getLogger().info("Successfully registered Mojang Brigadier support for commands");
        }

        // Setup exception handler
        new MinecraftExceptionHandler<CommandSender>()
                .withDefaultHandlers()
                .apply(this, AudienceProvider.nativeAudience()::apply);

        // Make the command instances "reloadable"
        setSetting(ManagerSettings.OVERRIDE_EXISTING_COMMANDS, true);
    }

    /**
     * Adds the specific command.
     * <p>
     * Adding a command is not registering it.
     * Added commands will not be effective until {@link #registerCommands()} is called.
     *
     * @param command the command to be added
     */
    public final void addCommand(final @NotNull Command<CommandSender> command) {
        storedCommands.add(command);
    }

    /**
     * Adds many commands at once. See {@link #addCommand(Command)} for more details.
     *
     * @param command the command to be added
     */
    public final void addCommand(final @NotNull Iterable<Command<CommandSender>> command) {
        for (final Command<CommandSender> one : command) {
            storedCommands.add(one);
        }
    }

    /**
     * Registers all added commands to the tree.
     * <p>
     * This method will make the added commands effective.
     */
    public final void registerCommands() {
        for (final Command<CommandSender> added : storedCommands) {
            command(added);
        }
    }
}
