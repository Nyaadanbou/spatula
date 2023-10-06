plugins {
    id("cc.mewcraft.publishing-conventions")
}

group = "cc.mewcraft.spatula.item"
version = "1.0.0"
description = "Centralize the code related to custom plugin items."

dependencies {
    // Server API
    compileOnly(libs.server.paper)
}