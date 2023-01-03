/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("UnstableApiUsage")

import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.testImplementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmJUnit4)
}

android {
    namespace = "team.duckie.app.android.util.viewmodel"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementations(
        libs.kotlin.coroutines,
        libs.androidx.lifecycle.savedstate,
        projects.utilKotlin,
    )
    testImplementations(
        libs.test.turbine,
        libs.test.coroutines,
        libs.compose.ui.activity,
        "org.robolectric:robolectric:4.9",
    )
}
