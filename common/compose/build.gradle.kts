import DependencyHandler.Extensions.implementations

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    alias(libs.plugins.duckie.android.library)
    alias(libs.plugins.duckie.android.library.compose)
}

android {
    namespace = "team.duckie.app.android.common.compose"
}

dependencies {
    implementations(
        projects.domain,
        projects.common.kotlin,
        libs.compose.lifecycle.viewmodel,
        libs.quack.ui.components,
        libs.kotlin.collections.immutable,
        libs.quack.v2.ui,
        libs.compose.ui.activity,
        libs.compose.ui.material,
        libs.compose.ui.camposer,
        libs.compose.ui.coil,
        libs.compose.ui.accompanist.placeholder,
        libs.paging.compose,
        libs.compose.ui.accompanist.flowlayout,
    )
}
