/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("UnstableApiUsage")

import DependencyHandler.Extensions.implementations

plugins {
    alias(libs.plugins.duckie.android.library)
}

android {
    namespace = "team.duckie.app.android.common.android"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.common.kotlin,
        libs.androidx.annotation,
        libs.firebase.dynamic.links,
        libs.firebase.crashlytics,
        libs.androidx.lifecycle.savedstate,
        libs.ktx.lifecycle.runtime,
        libs.ktx.lifecycle.viewmodel,
        libs.compose.ui.activity,
        libs.ui.system.controller,
    )
}
