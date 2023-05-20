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
}

android {
    namespace = "team.duckie.app.android.feature.photopicker"
}

dependencies {
    implementations(
        projects.common.kotlin,
        libs.quack.ui.components,
        libs.compose.ui.camposer,
    )
}
