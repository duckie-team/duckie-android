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
    namespace = "team.duckie.app.android.feature.ui.create.problem"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.di,
        projects.domain,
        projects.utilUi,
        projects.utilKotlin,
        projects.utilAndroid,
        projects.utilCompose,
        projects.featurePhotopicker,
        projects.sharedUiCompose,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.lifecycle.runtime,
        libs.compose.ui.material, // needs for Scaffold
        libs.quack.ui.components,
        libs.firebase.crashlytics,
    )
}
