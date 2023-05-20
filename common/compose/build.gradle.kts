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
    namespace = "team.duckie.app.android.common.compose"
}

dependencies {
    implementations(
        projects.common.kotlin,
        libs.compose.lifecycle.viewmodel,
        libs.quack.ui.components,
        libs.compose.ui.activity,
        libs.compose.ui.material,
        libs.compose.ui.coil,
        libs.compose.ui.accompanist.placeholder,
        libs.paging.compose,
    )
}
