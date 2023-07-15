/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import DependencyHandler.Extensions.implementations
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidHilt)
    id(ConventionEnum.AppVersionNameProvider)
    id(libs.plugins.gms.google.service.get().pluginId)
    id(libs.plugins.firebase.crashlytics.get().pluginId)
    id(libs.plugins.firebase.performance.get().pluginId)
    id(libs.plugins.util.secrets.get().pluginId)
    id(libs.plugins.ui.oss.license.get().pluginId)
}

android {
    namespace = "team.duckie.app.android"

    // TODO(sungbin): release signing key 등록
    signingConfigs {
        create("release") {
            val configFile = project.rootProject.file("signingconfig.properties")
            val properties = Properties()
            properties.load(FileInputStream(configFile))

            storeFile = project.rootProject.file(properties["storeFile"] as String)
            storePassword = properties["storePassword"] as String
            keyAlias = properties["keyAlias"] as String
            keyPassword = properties["keyPassword"] as String
        }
    }

    buildFeatures {
        buildConfig = true
    }

    // TODO(sungbin): flavorDimensions 가 정확히 뭘까?
    flavorDimensions.add("mode")
    productFlavors {
        create("alwaysRipple") {
            versionNameSuffix = "-AlwaysRipple"
            buildConfigField("boolean", "ALWAYS_RIPPLE", "true")
            buildConfigField("boolean", "IS_STAGE", "false")
        }

        create("standard") {
            buildConfigField("boolean", "ALWAYS_RIPPLE", "false")
            buildConfigField("boolean", "IS_STAGE", "false")
        }

        create("standardStage") {
            buildConfigField("boolean", "ALWAYS_RIPPLE", "false")
            buildConfigField("boolean", "IS_STAGE", "true")
            // TODO(riflockle7): push 나 통계 등을 생각하면 스테이지 환경, 리얼 환경은 applicationId 로 분리 되는 게 좋음
            //   추후 논의 반드시 하기
            // applicationIdSuffix = ".stage"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            // TODO(riflockle7): The Crashlytics build ID is missing. 가 발생하여 아래 코드 활성화가 불가능
            // isMinifyEnabled = true
            // isShrinkResources = true
            // proguardFiles(
            //     getDefaultProguardFile("proguard-android-optimize.txt"),
            //     "proguard-rules.pro",
            // )
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
        libs.logging.timber,
        projects.presentation, // for launch IntroActivity
        projects.feature.examResult,
        projects.feature.solveProblem,
        projects.feature.notification,
        projects.feature.createProblem,
        projects.feature.startExam,
        projects.feature.detail,
        projects.feature.profile,
        projects.feature.setting,
        projects.feature.friends,
        projects.feature.tagEdit,
        projects.feature.devMode,
        projects.core.datastore,
        projects.core.sync,
        projects.common.kotlin,
        projects.navigator,
    )
    debugImplementation(libs.analytics.leakcanary)
}
