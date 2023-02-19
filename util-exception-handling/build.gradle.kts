/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("UnstableApiUsage")

import DependencyHandler.Extensions.implementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    // id(ConventionEnum.JvmJUnit4)
}

android {
    namespace = "team.duckie.app.android.util.exception.handling"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        // projects.utilViewmodel,
        libs.androidx.annotation,
        libs.firebase.crashlytics,
    )
}
