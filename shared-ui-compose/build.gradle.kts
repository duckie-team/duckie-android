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
    id(ConventionEnum.JvmJUnit4)
}

android {
    namespace = "team.duckie.app.android.shared.ui.compose"
}

dependencies {
    implementations(
        libs.quack.ui.components,
        projects.utilCompose,
        projects.utilKotlin,
    )
}
