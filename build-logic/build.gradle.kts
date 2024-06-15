/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import DependencyHandler.Extensions.implementations

plugins {
    `kotlin-dsl`
    alias(libs.plugins.util.dependency.handler.extensions)
}

group = "team.duckie.app.android.local"

dependencies {
    implementations(
        libs.kotlin.core,
        libs.build.gradle.agp,
        libs.build.dependency.handler.extensions,
    )
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        val prefix = "duckie"
        register("appVersionNameProviderPlugin") {
            id = "$prefix.app.version.name.provider"
            implementationClass = "AppVersionNameProvider"
        }
        register("androidHiltPlugin") {
            id = "$prefix.android.hilt"
            implementationClass = "AndroidHiltPlugin"
        }
        register("androidApplicationPlugin") {
            id = "$prefix.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidLibraryPlugin") {
            id = "$prefix.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidLibraryComposePlugin") {
            id = "$prefix.android.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidRoom") {
            id = "$prefix.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibraryPlugin") {
            id = "$prefix.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }
        register("jvmJunitPlugin") {
            id = "$prefix.jvm.junit"
            implementationClass = "JvmJUnitPlugin"
        }
    }
}
