/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

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

buildCache {
    local {
        removeUnusedEntriesAfterDays = 7
    }
}

include(
    ":app",
    ":data",
    ":domain",
    ":presentation",
    ":feature-datastore",
    ":feature-ui-setting",
    ":feature-ui-onboard",
    ":feature-ui-notification",
    ":util-ui",
    ":util-viewmodel",
    ":util-kotlin",
    ":util-compose",
)
