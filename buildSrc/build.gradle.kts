/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    `kotlin-dsl`
}

dependencies {
    // Unresolved reference: implementations
    implementation(libs.kotlin.core)
    implementation(libs.build.gradle.agp)
    implementation("com.squareup:javapoet:1.13.0") {
        because("https://github.com/google/dagger/issues/3068#issuecomment-999118496")
    }
}
