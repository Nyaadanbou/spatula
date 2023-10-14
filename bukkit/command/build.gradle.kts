plugins {
    id("cc.mewcraft.repo-conventions")
    id("cc.mewcraft.java-conventions")
}

group = "cc.mewcraft.spatula.command"
version = "1.0.0"
description = "A wrapper of the cloud framework"

dependencies {
    // Server API
    compileOnly(libs.server.paper)

    // Backed command framework
    api(libs.bundles.cmds.paper) {
        exclude(group = "net.kyori") // already included by paper server
        exclude(group = "org.jetbrains") // already included by our setup
    }
}