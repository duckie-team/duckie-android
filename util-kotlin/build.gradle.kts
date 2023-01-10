/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    id(ConventionEnum.JvmLibrary)
    id(ConventionEnum.JvmJUnit4)
}

dependencies {
    detektPlugins(libs.detekt.plugin.formatting)
    implementation(libs.kotlin.coroutines)
}
