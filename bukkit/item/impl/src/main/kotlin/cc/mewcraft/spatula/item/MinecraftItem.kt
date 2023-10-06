package cc.mewcraft.spatula.item

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Just a convenience implementation.
 */
class MinecraftItem : PluginItem<Material>() {
    override val isLoaded: Boolean = true // vanilla items always available huh
    override val internalInstanceSupplier: () -> Material? = { Material.matchMaterial(itemId) }

    override fun createItemStack(): ItemStack? {
        return internalInstance?.let { ItemStack(it) }
    }

    override fun createItemStack(player: Player): ItemStack? {
        return this.createItemStack()
    }

    override fun matches(itemStack: ItemStack): Boolean {
        return !itemStack.hasItemMeta() && itemStack.type == internalInstance
    }

    override fun belongs(itemStack: ItemStack): Boolean {
        return !itemStack.hasItemMeta()
    }

    override fun asItemId(itemStack: ItemStack): String {
        return itemStack.type.getKey().value()
    }
}
