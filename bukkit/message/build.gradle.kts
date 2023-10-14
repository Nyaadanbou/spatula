plugins {
    id("cc.mewcraft.repo-conventions")
    id("cc.mewcraft.java-conventions")
}

group = "cc.mewcraft.spatula.message"
version = "1.0.0"
description = "A basic i18n framework"

dependencies {
    // Server API
    compileOnly(libs.server.paper)

    // Backed i18n framework
    api(libs.lang.bukkit) // TODO change it to `implementation` when all projects do not depend on it directly
}