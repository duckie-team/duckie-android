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
    id(ConventionEnum.AndroidHilt)
    `kotlin-parcelize`
}

android {
    namespace = "team.duckie.app.android.feature.onboard"
}

dependencies {
    implementations(
        projects.feature.home,
        projects.di,
        projects.domain,
        projects.navigator,
        projects.common.kotlin,
        projects.common.compose,
        projects.common.android,
        projects.core.datastore,
        libs.firebase.messaging,
        libs.apache.commons.io,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.ui.material, // needs for ModalBottomSheet
        libs.compose.ui.foundation,
        libs.compose.lifecycle.runtime,
        libs.compose.ui.accompanist.flowlayout,
        libs.kotlin.collections.immutable,
        libs.quack.v2.ui,
        libs.compose.ui.lottie,
    )
}
