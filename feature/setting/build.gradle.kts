/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import AppVersionNameProvider.App.VersionCode
import AppVersionNameProvider.App.VersionName
import DependencyHandler.Extensions.implementations

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidHilt)
    id(ConventionEnum.AppVersionNameProvider)
}

android {
    namespace = "team.duckie.app.android.feature.setting"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "APP_VERSION_NAME", "\"$VersionName.$VersionCode\"")
    }
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        projects.di,
        projects.domain,
        projects.navigator,
        projects.utilUi,
        projects.utilKotlin,
        projects.utilCompose,
        projects.sharedUiCompose,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.quack.ui.components,
        libs.compose.lifecycle.runtime,
        libs.compose.ui.accompanist.webview,
        libs.firebase.crashlytics,
        libs.ui.oss.license,
        libs.androidx.appcompat,
    )
}
