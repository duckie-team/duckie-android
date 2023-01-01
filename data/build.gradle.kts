/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.testImplementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.AndroidHilt)
    id(libs.plugins.util.secrets.get().pluginId)
}

android {
    namespace = "team.duckie.app.android.data"

    buildFeatures {
        buildConfig = true
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
    )
    testImplementations(
        libs.test.coroutines,
        libs.test.ktor.client,
    )
}
