package cc.mewcraft.spatula.item

import net.leonardo_dgs.interactivebooks.IBook
import net.leonardo_dgs.interactivebooks.InteractiveBooks
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class InteractiveBooksItem : PluginItem<IBook>() {
    override val isLoaded: Boolean get() = hasPlugin("InteractiveBooks")
    override val internalInstanceSupplier: () -> IBook? = { InteractiveBooks.getBook(itemId) }

    override fun createItemStack(): ItemStack? {
        return internalInstance?.item?.apply { amount = 1 }
    }

    override fun createItemStack(player: Player): ItemStack? {
        return internalInstance?.getItem(player)?.apply { amount = 1 }
    }

    override fun matches(itemStack: ItemStack): Boolean {
        return InteractiveBooks.getBook(itemStack)?.id.equals(itemId, ignoreCase = true)
    }

    override fun belongs(itemStack: ItemStack): Boolean {
        return itemStack.type == Material.WRITTEN_BOOK &&
                InteractiveBooks.getBook(itemStack) != null
    }

    override fun asItemId(itemStack: ItemStack): String? {
        return InteractiveBooks.getBook(itemStack)?.id
    }
}
