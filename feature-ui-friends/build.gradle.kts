/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
import DependencyHandler.Extensions.implementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidHilt)
}

android {
    namespace = "team.duckie.app.android.feature.ui.friends"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.domain,
        projects.di,
        projects.navigator,
        projects.utilUi,
        projects.sharedUiCompose,
        projects.utilAndroid,
        projects.utilKotlin,
        projects.utilExceptionHandling,
        projects.utilCompose,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.ui.material, // needs for Scaffold
        libs.compose.lifecycle.runtime,
        libs.quack.ui.components,
    )
}