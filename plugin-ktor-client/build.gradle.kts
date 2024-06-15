/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.testImplementations

plugins {
    alias(libs.plugins.duckie.jvm.library)
    alias(libs.plugins.duckie.jvm.junit)
}

dependencies {
    detektPlugins(libs.detekt.plugin.formatting)
    implementations(libs.ktor.client.core)
    testImplementations(
        libs.ktor.client.engine,
        libs.test.coroutines,
    )
}
