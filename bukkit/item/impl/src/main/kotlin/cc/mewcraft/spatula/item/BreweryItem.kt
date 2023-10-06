package cc.mewcraft.spatula.item

import com.dre.brewery.api.BreweryApi
import com.dre.brewery.recipe.BRecipe
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class BreweryItem : PluginItem<BRecipe>() {
    override val isLoaded: Boolean get() = hasPlugin("Brewery")
    override val internalInstanceSupplier: () -> BRecipe? = { throw NotImplementedError() }

    override fun createItemStack(): ItemStack? {
        with(itemId.split('~')) {
            if (size != 2) {
                error("[Brewery] The format of Brewery item should be `brewery:{recipe}~{quality}`")
            }
            val recipeName = this[0]
            val brewQuality = this[1].toInt()
            return BreweryApi.createBrewItem(recipeName, brewQuality)
        }
    }

    override fun createItemStack(player: Player): ItemStack? {
        return createItemStack()
    }

    override fun matches(itemStack: ItemStack): Boolean {
        val brew = BreweryApi.getBrew(itemStack) ?: return false
        val otherRecipe = brew.currentRecipe.getName(brew.quality)
        val thisRecipe: String = itemId.split("~")[0]
        return otherRecipe.equals(thisRecipe, ignoreCase = true)
    }

    override fun belongs(itemStack: ItemStack): Boolean {
        return BreweryApi.isBrew(itemStack)
    }

    override fun asItemId(itemStack: ItemStack): String? {
        val brew = BreweryApi.getBrew(itemStack) ?: return null
        val recipeName = brew.currentRecipe.getName(brew.quality)
        val brewQuality = brew.quality
        return "$recipeName~$brewQuality".lowercase()
    }
}