plugins {
    id("cc.mewcraft.repo-conventions")
    id("cc.mewcraft.java-conventions")
}

group = "cc.mewcraft.spatula.network"
version = "1.0.0"
description = "Provides common variables of the network based on LuckPerms"

dependencies {
    // Backed data storage
    compileOnly(libs.luckperms)
}