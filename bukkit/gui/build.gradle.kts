plugins {
    id("cc.mewcraft.repo-conventions")
    id("cc.mewcraft.kotlin-conventions")
}

dependencies {
    api(libs.bundles.invui) {
        exclude("org.jetbrains") // already included by our setup
        exclude("org.jetbrains.kotlin") // the kotlin is outdated for us
    }

    compileOnly(libs.server.paper)
}