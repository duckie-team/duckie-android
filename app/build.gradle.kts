/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import DependencyHandler.Extensions.implementations

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidHilt)
    id(libs.plugins.gms.google.service.get().pluginId)
    id(libs.plugins.firebase.crashlytics.get().pluginId)
    id(libs.plugins.firebase.performance.get().pluginId)
    id(libs.plugins.util.secrets.get().pluginId)
}

android {
    namespace = "team.duckie.app.android"

    // TODO(sungbin): release signing key 등록

    buildFeatures {
        buildConfig = true
    }

    // TODO(sungbin): flavorDimensions 가 정확히 뭘까?
    flavorDimensions.add("mode")
    productFlavors {
        create("alwaysRipple") {
            versionNameSuffix = "-AlwaysRipple"
            buildConfigField("boolean", "ALWAYS_RIPPLE", "true")
        }

        create("standard") {
            buildConfigField("boolean", "ALWAYS_RIPPLE", "false")
        }
    }
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        libs.analytics.anrwatchdog,
        libs.firebase.performance,
        libs.firebase.analytics,
        libs.firebase.crashlytics,
        libs.login.kakao, // for KakaoSDK initialization
        libs.quack.ui.components, // for debug setting
        projects.presentation, // for launch IntroActivity
        projects.utilKotlin,
        projects.navigator,
    )
    debugImplementation(libs.analytics.leakcanary)
}
