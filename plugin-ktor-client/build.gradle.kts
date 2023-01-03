/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandler.Extensions.testImplementations

plugins {
    id(ConventionEnum.JvmLibrary)
    id(ConventionEnum.JvmJUnit4)
}

dependencies {
    implementation(libs.ktor.client)
    testImplementations(
        libs.ktor.engine,
        libs.test.coroutines,
    )
}
