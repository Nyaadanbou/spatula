package cc.mewcraft.spatula.item

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.slf4j.Logger

internal val logger: Logger by lazy {
    plugin.slF4JLogger
}

internal val plugin: Plugin by lazy {
    Bukkit.getPluginManager().getPlugin("SpatulaItems") ?: error("The standalone plugin is not loaded")
}

object PluginItemRegistry {
    private val constructors: MutableMap<String, () -> PluginItem<*>> = LinkedHashMap()

    fun register(pluginId: String, constructor: () -> PluginItem<*>) {
        constructors[pluginId.lowercase()] = constructor
    }

    fun unregister(pluginId: String) {
        constructors.remove(pluginId.lowercase())
    }

    fun unregisterAll() {
        constructors.clear()
    }

    /**
     * Updates the specific [itemStack] to its latest version if there is any.
     *
     * @return a new ItemStack if the [itemStack] is from a known plugin, or [itemStack] as-is if it is not from a known plugin
     */
    fun refresh(itemStack: ItemStack): ItemStack {
        return byItemStackOrNull(itemStack)?.createItemStack()?.apply { amount = itemStack.amount } ?: itemStack
    }

    /**
     * Creates corresponding [PluginItem] derived from the specific [itemStack] or null, if the [itemStack] is not valid.
     */
    fun byItemStackOrNull(itemStack: ItemStack): PluginItem<*>? {
        for ((pluginId, supplier) in constructors) {
            val pluginItem = supplier()
            if (!pluginItem.isLoaded) continue
            if (pluginItem.belongs(itemStack)) {
                return pluginItem.apply {
                    this._pluginId = pluginId
                    this._itemId = itemStack.asItemId(this)
                }
            }
        }
        return null
    }

    /**
     * Creates corresponding [PluginItem] derived from the specific [pluginId] and [itemId] or null, if the reference is not valid.
     */
    fun byReferenceOrNull(pluginId: String, itemId: String): PluginItem<*>? {
        val pluginItem = constructors[pluginId.lowercase()]?.invoke()?.apply {
            if (!isLoaded) return null
            this._pluginId = pluginId
            this._itemId = itemId
        }
        if (pluginItem == null) {
            logger.error("Unsupported item with id `$itemId` from plugin `$pluginId`.")
        }
        return pluginItem
    }

    /**
     * Creates the corresponding [PluginItem] derived from the specific [reference] or null, if the reference is not valid.
     */
    fun byReferenceOrNull(reference: String): PluginItem<*>? {
        with(reference.split(':', limit = 2).toTypedArray()) {
            if (size != 2) {
                logger.error("Illegal reference: `$reference`")
                return null
            }
            return byReferenceOrNull(this[0], this[1])
        }
    }

    fun byItemStack(itemStack: ItemStack): PluginItem<*> =
        byItemStackOrNull(itemStack) ?: error("Cannot find PluginItem with ItemStack: `$itemStack`")

    fun byReference(reference: String): PluginItem<*> =
        byReferenceOrNull(reference) ?: error("Cannot find PluginItem with reference: `$reference`")

    fun byReference(plugin: String, itemId: String): PluginItem<*> =
        byReferenceOrNull(plugin, itemId) ?: error("Cannot find PluginItem with reference: `$plugin:$itemId`")
}
