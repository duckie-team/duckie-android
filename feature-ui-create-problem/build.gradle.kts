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
    id(ConventionEnum.AndroidLibraryComposeUiTest)
    id(ConventionEnum.AndroidHilt)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
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
        projects.utilCompose,
        projects.utilViewmodel,
        projects.featurePhotopicker,
        projects.sharedUiCompose,
        libs.ktx.lifecycle,
        libs.compose.ktx.lifecycle,
        libs.compose.ui.material, // needs for Scaffold
        libs.quack.ui.components,
        libs.firebase.crashlytics,
    )
}
