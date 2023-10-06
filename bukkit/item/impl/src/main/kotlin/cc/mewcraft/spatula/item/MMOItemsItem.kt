package cc.mewcraft.spatula.item

import io.lumine.mythic.lib.api.item.NBTItem
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

private val plugin: Plugin by lazy {
    Bukkit.getPluginManager().getPlugin("SpatulaItems") ?: error("The standalone plugin is not loaded")
}

class MMOItemsItem : PluginItem<MMOItemTemplate>() {
    override val isLoaded: Boolean get() = hasPlugin("MMOItems")
    override val internalInstanceSupplier: () -> MMOItemTemplate? = {
        val itemId = itemId.uppercase().split(':').toTypedArray()
        val type = MMOItems.plugin.types.get(itemId[0]) ?: error("[MMOItems] Could not found item type: `${itemId[0]}`")
        MMOItems.plugin.templates.getTemplate(type, itemId[1]) ?: error("[MMOItems] Could not found item: ${itemId[1]} (type: ${itemId[0]})")
    }

    override fun createItemStack(): ItemStack? {
        return internalInstance?.let {
            val supplier = { it.newBuilder().build().newBuilder().build() }
            if (Bukkit.isPrimaryThread()) {
                supplier.invoke()
            } else {
                Bukkit.getScheduler().callSyncMethod(plugin) { supplier }.get().invoke()
            }
        }
    }

    override fun createItemStack(player: Player): ItemStack? {
        return internalInstance?.let {
            val supplier = { it.newBuilder(player).build().newBuilder().build() }
            if (Bukkit.isPrimaryThread()) {
                supplier.invoke()
            } else {
                Bukkit.getScheduler().callSyncMethod(plugin) { supplier }.get().invoke()
            }
        }
    }

    override fun matches(itemStack: ItemStack): Boolean {
        val nbtItem: NBTItem = NBTItem.get(itemStack)
        if (!nbtItem.hasType()) return false
        val type: String = nbtItem.type
        val id: String = nbtItem.getString("MMOITEMS_ITEM_ID")
        return itemId.equals("$type:$id", ignoreCase = true)
    }

    override fun belongs(itemStack: ItemStack): Boolean {
        return NBTItem.get(itemStack).hasType()
    }

    override fun asItemId(itemStack: ItemStack): String? {
        val nbtItem: NBTItem = NBTItem.get(itemStack)
        if (!nbtItem.hasType()) return null
        val type: String = nbtItem.type
        val id: String = nbtItem.getString("MMOITEMS_ITEM_ID")
        return "$type:$id".lowercase()
    }
}
