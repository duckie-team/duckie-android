/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandler.Extensions.implementations

plugins {
    alias(libs.plugins.duckie.android.library)
    alias(libs.plugins.duckie.android.library.compose)
    alias(libs.plugins.duckie.android.hilt)
}

android {
    namespace = "team.duckie.app.android.feature.start.exam"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.di,
        projects.domain,
        projects.common.android,
        projects.navigator,
        projects.common.kotlin,
        projects.common.compose,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.quack.v2.ui,
        libs.compose.lifecycle.runtime,
        libs.compose.ui.material, // needs for CircularProgressIndicator
        libs.firebase.crashlytics,
    )
}
