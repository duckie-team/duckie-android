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
    id(ConventionEnum.AndroidLibraryComposeUiTest)
    id(ConventionEnum.AndroidHilt)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
}

android {
    namespace = "team.duckie.app.android.feature.ui.search"
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
        projects.sharedUiCompose,
        libs.quack.ui.components,
        libs.compose.ktx.lifecycle,
        libs.compose.ui.coil,
        libs.firebase.crashlytics,
        libs.paging.runtime,
        libs.paging.compose,
    )
}
