plugins {
    id("cc.mewcraft.repo-conventions")
    id("cc.mewcraft.java-conventions")
}

dependencies {
    // server
    compileOnly(libs.server.paper)
}