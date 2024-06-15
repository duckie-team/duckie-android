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
    ":navigator",
    ":core:datastore",
    ":core:sync",
    ":feature:skeleton",
    ":feature:search",
    ":feature:setting",
    ":feature:onboard",
    ":feature:notification",
    ":feature:home",
    ":feature:start-exam",
    ":feature:solve-problem",
    ":feature:create-exam",
    ":feature:detail",
    ":feature:exam-result",
    ":feature:profile",
    ":feature:friends",
    ":feature:tag-edit",
    ":feature:dev-mode",
    ":common:kotlin",
    ":common:android",
    ":common:compose",
    ":plugin-ktor-client",
)
