/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import DependencyHandlerExtensions.Companion.implementations

plugins {
    `kotlin-dsl`
    alias(libs.plugins.dependency.handler.extensions)
}

dependencies {
    implementations(
        libs.kotlin.core,
        libs.build.gradle.agp,
    )
    implementation("com.squareup:javapoet:1.13.0") {
        because("https://github.com/google/dagger/issues/3068#issuecomment-999118496")
    }
}
