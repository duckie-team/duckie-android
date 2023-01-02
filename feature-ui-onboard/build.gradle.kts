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
    namespace = "team.duckie.app.android.feature.ui.onboard"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.di,
        projects.domain,
        projects.utilUi,
        projects.utilKotlin,
        projects.utilCompose,
        projects.utilViewmodel,
        projects.featureDatastore,
        projects.featurePhotopicker,
        projects.sharedUiCompose,
        projects.pluginKtorClient,
        libs.ktx.lifecycle,
        libs.firebase.crashlytics,
        libs.compose.ui.material, // needs for ModalBottomSheet
        libs.compose.ktx.lifecycle,
        libs.compose.ui.accompanist.flowlayout,
        libs.quack.ui.components,
    )
}
