package cc.mewcraft.spatula.item

import org.bukkit.Bukkit

internal fun hasPlugin(name: String): Boolean =
    Bukkit.getPluginManager().getPlugin(name) != null