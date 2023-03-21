/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import AppVersionNameProvider.App.VersionName
import DependencyHandler.Extensions.androidTestImplementations
import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.ksps
import DependencyHandler.Extensions.testImplementations
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.AndroidHilt)
    id(ConventionEnum.AppVersionNameProvider)
    id(ConventionEnum.AndroidRoom)
    id(libs.plugins.kotlin.ksp.get().pluginId)
}

android {
    namespace = "team.duckie.app.android.data"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "APP_VERSION_NAME", "\"$VersionName\"")
        manifestPlaceholders["KAKAO_MANIFEST_SCHEME"] =
            gradleLocalProperties(rootDir).getProperty("KAKAO_MANIFEST_SCHEME")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

// TODO(sungbin): ktor 의존성 제거 및 mockk 의존성 추가
// https://github.com/duckie-team/duckie-android/issues/180
dependencies {
    implementations(
        libs.login.kakao,
        libs.kotlin.coroutines,
        libs.kotlin.collections.immutable,
        libs.paging.runtime,
        libs.jackson.databind,
        libs.bundles.fuel,
        libs.bundles.moshi,
        libs.bundles.ktor.client,
        projects.domain,
        projects.utilKotlin,
        projects.pluginKtorClient,
        projects.featureDatastore,
    )
    testImplementations(
        libs.test.coroutines,
        libs.test.ktor.client,
        libs.test.ktor.server, // for E2E test
        libs.ktor.server.core, // for E2E test
    )
    androidTestImplementations(
        libs.test.androidx.junit,
        libs.test.androidx.runner,
        libs.test.strikt,
    )
    implementation(libs.ktx.lifecycle.viewmodel) { // for androidTest
        exclude(group = "androidx.lifecycle", module = "lifecycle-viewmodel")
    }
}
