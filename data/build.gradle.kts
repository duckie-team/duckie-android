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
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    implementations(
        libs.login.kakao,
        libs.kotlin.coroutines,
        libs.kotlin.collections.immutable,
        libs.paging.runtime,
        libs.bundles.ktor.client,
        projects.domain,
        projects.utilKotlin,
        projects.pluginKtorClient,
    )
    testImplementations(
        libs.test.coroutines,
        libs.test.ktor.client,
        libs.test.ktor.server, // for E2E test
        libs.ktor.server.core, // for E2E test
    )
}
