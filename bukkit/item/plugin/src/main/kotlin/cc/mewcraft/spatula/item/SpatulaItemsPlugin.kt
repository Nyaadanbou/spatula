package cc.mewcraft.spatula.item

import org.bukkit.plugin.java.JavaPlugin

class SpatulaItemsPlugin : JavaPlugin() {
    private val logger = componentLogger

    override fun onLoad() {
        registerOrNothing("Brewery", "brewery") { BreweryItem() }
        registerOrNothing("MMOItems", "mmoitems") { MMOItemsItem() }
        registerOrNothing("ItemsAdder", "itemsadder") { ItemsAdderItem() }
        registerOrNothing("InteractiveBooks", "interactivebooks") { InteractiveBooksItem() }

        // MinecraftItem must be the last
        registerOrNothing("Minecraft", "minecraft", true) { MinecraftItem() }
    }

    override fun onDisable() {
        PluginItemRegistry.unregisterAll()
    }

    private fun registerOrNothing(
        plugin: String,
        namespace: String,
        skipCheck: Boolean = false,
        supplier: () -> PluginItem<*>,
    ) {
        val pluginName = server.pluginManager.getPlugin(plugin)?.name
        if (pluginName != null || skipCheck) {
            PluginItemRegistry.register(namespace, supplier)
            logger.info("Registered for plugin: $plugin")
        } else {
            logger.warn("Plugin `$plugin` is not loaded in the server, skipped")
        }
    }
}
