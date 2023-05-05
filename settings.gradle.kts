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
    ":navigator",
    ":feature-datastore",
    ":feature-photopicker",
    ":feature-messaging",
    ":feature-ui-search",
    ":feature-ui-setting",
    ":feature-ui-onboard",
    ":feature-ui-notification",
    ":feature-ui-home",
    ":feature-ui-start-exam",
    ":feature-ui-solve-problem",
    ":feature-ui-create-problem",
    ":feature-ui-detail",
    ":feature-ui-exam-result",
    ":feature-ui-profile",
    ":feature-ui-friends",
    ":feature-ui-tag-edit",
    ":shared-ui-compose",
    ":util-ui",
    ":util-kotlin",
    ":util-android",
    ":util-compose",
    ":util-exception-handling",
    ":plugin-ktor-client",
)
