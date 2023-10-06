package cc.mewcraft.spatula.item

import dev.lone.itemsadder.api.CustomStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemsAdderItem : PluginItem<CustomStack>() {
    override val isLoaded: Boolean get() = hasPlugin("ItemsAdder")
    override val internalInstanceSupplier: () -> CustomStack? = { CustomStack.getInstance(itemId) }

    override fun createItemStack(): ItemStack? {
        return internalInstance?.itemStack?.apply { amount = 1 }
    }

    override fun createItemStack(player: Player): ItemStack? {
        return createItemStack()
    }

    override fun matches(itemStack: ItemStack): Boolean {
        return asItemId(itemStack)?.equals(itemId, ignoreCase = true) == true
    }

    override fun belongs(itemStack: ItemStack): Boolean {
        return CustomStack.byItemStack(itemStack) != null
    }

    override fun asItemId(itemStack: ItemStack): String? {
        return CustomStack.byItemStack(itemStack)?.namespacedID
    }
}