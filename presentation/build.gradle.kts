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
    `kotlin-parcelize`
}

android {
    namespace = "team.duckie.app.android.presentation"
}

dependencies {
    implementations(
        projects.di,
        projects.navigator,
        projects.utilUi,
        projects.utilCompose,
        projects.utilKotlin,
        projects.featureDatastore,
        projects.featureUiHome,
        projects.featureUiOnboard,
        projects.utilExceptionHandling,
        projects.domain,
        libs.orbit.viewmodel,
        libs.androidx.splash,
        libs.quack.ui.components,
    )
}
