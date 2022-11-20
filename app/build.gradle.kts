/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(ConventionEnum.AndroidApplication)
    id(libs.plugins.di.hilt.get().pluginId)
    id(libs.plugins.gms.google.service.get().pluginId)
    id(libs.plugins.firebase.crashlytics.get().pluginId)
    id(libs.plugins.firebase.performance.get().pluginId)
    `kotlin-kapt`
}

android {
    namespace = "team.duckie.app.android"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        libs.firebase.performance,
        libs.firebase.analytics,
        libs.firebase.crashlytics,
        libs.di.hilt.core,
        projects.presentation,
    )
    kapt(libs.di.hilt.compiler)
}
