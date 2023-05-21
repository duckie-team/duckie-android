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
}

android {
    namespace = "team.duckie.app.android.util.compose"
}

dependencies {
    implementations(
        projects.utilKotlin,
        libs.compose.lifecycle.viewmodel,
        libs.quack.ui.components,
        libs.quack.v2.ui,
        libs.compose.ui.activity,
        libs.paging.compose,
    )
}
