/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import AppVersionNameProvider.App.VersionName
import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.testImplementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.AndroidHilt)
    id(ConventionEnum.AppVersionNameProvider)
}

android {
    namespace = "team.duckie.app.android.data"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "APP_VERSION_NAME", "\"$VersionName\"")
    }
}

dependencies {
    implementations(
        libs.login.kakao,
        libs.kotlin.coroutines,
        libs.kotlin.collections.immutable,
        libs.bundles.ktor,
        projects.domain,
        projects.utilKotlin,
        projects.pluginKtorClient,
    )
    testImplementations(
        libs.test.coroutines,
        libs.test.ktor.client,
    )
}
