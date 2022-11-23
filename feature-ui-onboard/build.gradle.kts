/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandlerExtensions.Companion.implementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidLibraryComposeUiTest)
    id(ConventionEnum.AndroidHilt)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
}

android {
    namespace = "team.duckie.app.android.feature.ui.onboard"
}

dependencies {
    implementations(
        projects.utilUi,
        projects.utilKotlin,
        projects.utilCompose,
        projects.utilViewmodel,
        projects.featureDatastore,
        libs.compose.ui.material, // needs for ModalBottomSheet
        libs.compose.ktx.lifecycle,
        libs.quack.ui.components,
    )
}
