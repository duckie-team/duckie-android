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
    namespace = "team.duckie.app.android.feature.ui.start.exam"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.di,
        projects.domain,
        projects.utilUi,
        projects.utilKotlin,
        projects.utilCompose,
        projects.sharedUiCompose,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.quack.ui.components,
        libs.compose.lifecycle.runtime,
        libs.compose.ui.material, // needs for CircularProgressIndicator
        libs.firebase.crashlytics,
    )
}
