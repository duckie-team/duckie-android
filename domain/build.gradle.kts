/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandler.Extensions.implementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
}

android {
    namespace = "team.duckie.app.android.domain"
}

dependencies {
    implementations(
        libs.compose.runtime, // needs for Stability
        libs.kotlin.collections.immutable,
        libs.di.inject,
        libs.paging.common,
        projects.utilKotlin,
    )
}
