package cc.mewcraft.spatula.gui

import net.kyori.adventure.text.Component
import xyz.xenondevs.invui.item.builder.AbstractItemBuilder

/**
 * Sets the display name of the item stack.
 */
fun <T : AbstractItemBuilder<T>> T.setDisplayName(displayName: Component): T = setDisplayName(AdventureComponentWrapper(displayName))

/**
 * Sets the lore the item stack.
 */
fun <T : AbstractItemBuilder<T>> T.setLore(lore: List<Component>): T = setLore(lore.map { AdventureComponentWrapper(it) })

/**
 * Adds lore lines to the item stack.
 */
fun <T : AbstractItemBuilder<T>> T.addLoreLines(vararg components: Component): T = addLoreLines(*components.map { AdventureComponentWrapper(it) }.toTypedArray())