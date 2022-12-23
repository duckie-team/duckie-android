/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("UnstableApiUsage")

rootProject.name = "duckie"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Plugin 'com.google.gms.google-services': registration of listener on 'Gradle.addListener' is unsupported
// enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    pluginManagement {
        repositories {
            google()
            mavenLocal()
            mavenCentral()
            gradlePluginPortal()
        }
    }

    includeBuild("build-logic")
    includeBuild("build-logic/local-enums")
}

buildCache {
    local {
        removeUnusedEntriesAfterDays = 7
    }
}

include(
    ":di",
    ":app",
    ":data",
    ":domain",
    ":presentation",
    ":feature-datastore",
    ":feature-photopicker",
    ":feature-ui-setting",
    ":feature-ui-onboard",
    ":feature-ui-notification",
    ":feature-ui-home",
    ":feature-ui-solve-problem",
    ":feature-ui-create-problem",
    ":feature-ui-detail",
    ":util-ui",
    ":util-viewmodel",
    ":util-kotlin",
    ":util-compose",
    ":shared-ui-compose",
)
