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
        projects.utilCompose,
        projects.common.kotlin,
        projects.common.android,
        projects.core.datastore,
        projects.feature.home,
        projects.feature.onboard,
        projects.utilExceptionHandling,
        projects.domain,
        projects.sharedUiCompose,
        libs.orbit.viewmodel,
        libs.androidx.splash,
        libs.quack.ui.components,
        libs.orbit.compose,
    )
}
