plugins {
    id("cc.mewcraft.repo-conventions")
    id("cc.mewcraft.kotlin-conventions")
}

dependencies {
    api(libs.koin) {
        exclude("org.jetbrains.kotlin")
    }
}