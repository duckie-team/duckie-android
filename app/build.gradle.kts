/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import DependencyHandlerExtensions.Companion.implementations

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidHilt)
    id(libs.plugins.gms.google.service.get().pluginId)
    id(libs.plugins.firebase.crashlytics.get().pluginId)
    id(libs.plugins.firebase.performance.get().pluginId)
}

android {
    namespace = "team.duckie.app.android"
}

dependencies {
    implementations(
        platform(libs.firebase.bom),
        libs.analytics.anrwatchdog,
        libs.firebase.performance,
        libs.firebase.analytics,
        libs.firebase.crashlytics,
        projects.presentation,
    )
    debugImplementation(libs.analytics.leakcanary)
}
