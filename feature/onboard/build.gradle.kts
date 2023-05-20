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
    namespace = "team.duckie.app.android.feature.onboard"
}

dependencies {
    implementations(
        projects.feature.home,
        projects.di,
        projects.domain,
        projects.navigator,
        projects.utilUi,
        projects.utilKotlin,
        projects.utilCompose,
        projects.utilAndroid,
        projects.utilExceptionHandling,
        projects.featureDatastore,
        projects.featurePhotopicker,
        projects.sharedUiCompose,
        libs.apache.commons.io,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.ui.material, // needs for ModalBottomSheet
        libs.compose.lifecycle.runtime,
        libs.compose.ui.accompanist.flowlayout,
        libs.quack.ui.components,
    )
}
