plugins {
    id("cc.mewcraft.repo-conventions")
    id("cc.mewcraft.java-conventions")
}

dependencies {
    api(libs.guice) {
        exclude("com.google.guava") // already included by paper server
    }
}