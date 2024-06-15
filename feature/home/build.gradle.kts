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
    namespace = "team.duckie.app.android.feature.home"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.di,
        projects.domain,
        projects.navigator,
        projects.common.android,
        projects.common.kotlin,
        projects.common.compose,
        projects.feature.search,
        projects.core.datastore,
        projects.feature.profile,
        projects.navigator,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.kotlin.collections.immutable,
        libs.quack.v2.ui,
        libs.compose.lifecycle.runtime,
        libs.compose.ui.navigation,
        libs.compose.ui.coil,
        libs.compose.ui.material,
        libs.firebase.crashlytics,
        libs.paging.runtime,
        libs.paging.compose,
    )
}
