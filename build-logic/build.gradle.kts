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
    alias(libs.plugins.kotlin.dokka)
    alias(libs.plugins.util.dependency.handler.extensions)
    alias(libs.plugins.local.convention.enum)
}

group = "team.duckie.app.android.local"

dependencies {
    implementations(
        libs.kotlin.core,
        libs.kotlin.dokka.base,
        libs.kotlin.dokka.plugin,
        libs.build.gradle.agp,
        libs.build.local.plugin.enum,
        libs.build.dependency.handler.extensions,
    )
}

gradlePlugin {
    plugins {
        register("appVersionNameProviderPlugin") {
            id = ConventionEnum.AppVersionNameProvider
            implementationClass = "AppVersionNameProvider"
        }
        register("androidHiltPlugin") {
            id = ConventionEnum.AndroidHilt
            implementationClass = "AndroidHiltPlugin"
        }
        register("androidApplicationPlugin") {
            id = ConventionEnum.AndroidApplication
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidApplicationComposePlugin") {
            id = ConventionEnum.AndroidApplicationCompose
            implementationClass = "AndroidApplicationComposePlugin"
        }
        register("androidLibraryPlugin") {
            id = ConventionEnum.AndroidLibrary
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidLibraryComposePlugin") {
            id = ConventionEnum.AndroidLibraryCompose
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidLibraryComposeUiTestPlugin") {
            id = ConventionEnum.AndroidLibraryComposeUiTest
            implementationClass = "AndroidLibraryComposeUiTestPlugin"
        }
        register("jvmLibraryPlugin") {
            id = ConventionEnum.JvmLibrary
            implementationClass = "JvmLibraryPlugin"
        }
        register("jvmJunit4Plugin") {
            id = ConventionEnum.JvmJUnit4
            implementationClass = "JvmJUnit4Plugin"
        }
        register("jvmDokkaPlugin") {
            id = ConventionEnum.JvmDokka
            implementationClass = "JvmDokkaPlugin"
        }
    }
}
