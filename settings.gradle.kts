@file:Suppress("UnstableApiUsage")

rootProject.name = "duckie"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(
    ":data",
    ":domain",
    ":presentation",
    ":feature-setting",
    ":feature-onboard",
    ":feature-notification-screen",
)
