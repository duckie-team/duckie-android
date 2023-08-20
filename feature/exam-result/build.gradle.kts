import DependencyHandler.Extensions.implementations

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidHilt)
}

android {
    namespace = "team.duckie.app.android.feature.exam.result"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.di,
        projects.domain,
        projects.navigator,
        projects.common.kotlin,
        projects.common.compose,
        projects.common.android,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.ui.coil,
        libs.compose.lifecycle.runtime,
        libs.compose.ui.material, // needs for Scaffold
        libs.quack.v2.ui,
        libs.kotlin.collections.immutable,
        libs.firebase.crashlytics,
    )
}
