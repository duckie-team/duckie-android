/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.testImplementations

plugins {
    id(ConventionEnum.JvmLibrary)
    id(ConventionEnum.JvmJUnit4)
}

dependencies {
    implementations(
        libs.ktor.client,
        libs.ktor.engine,
    )
    testImplementations(
        libs.test.ktor.client,
        libs.test.coroutines,
    )
}
