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
    namespace = "team.duckie.app.android.feature.detail"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.domain,
        projects.di,
        projects.navigator,
        projects.utilUi,
        projects.core.datastore,
        projects.sharedUiCompose,
        projects.common.android,
        projects.common.kotlin,
        projects.utilExceptionHandling,
        projects.utilCompose,
        projects.feature.startExam,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.ui.material, // needs for Scaffold
        libs.compose.lifecycle.runtime,
        libs.quack.ui.components,
        libs.compose.ui.coil,
    )
}
