/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    alias(libs.plugins.duckie.jvm.library)
    alias(libs.plugins.duckie.jvm.junit)
}

dependencies {
    detektPlugins(libs.detekt.plugin.formatting)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.collections.immutable)
}
