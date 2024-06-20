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
    namespace = "team.duckie.app.android.feature.detail"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.domain,
        projects.di,
        projects.navigator,
        projects.core.datastore,
        projects.common.android,
        projects.common.kotlin,
        projects.common.compose,
        libs.firebase.dynamic.links,
        libs.kotlin.collections.immutable,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.ui.material, // needs for Scaffold
        libs.compose.lifecycle.runtime,
        libs.quack.v2.ui,
        libs.compose.ui.coil,
    )
}
