/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandler.Extensions.implementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmJUnit4)
}

android {
    namespace = "team.duckie.app.android.util.ui"
}

dependencies {
    implementations(
        libs.compose.ui.activity,
        libs.ui.system.controller,
    )
}
