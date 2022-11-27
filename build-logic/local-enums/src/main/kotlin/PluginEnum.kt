/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginEnum : Plugin<Project> {
    override fun apply(target: Project) = Unit

    companion object {
        const val KotlinCore = "kotlin"
        const val KotlinKapt = "kotlin-kapt"
        const val KotlinAndroid = "kotlin-android"

        const val JavaLibrary = "java-library"

        const val AndroidApplication = "com.android.application"
        const val AndroidLibrary = "com.android.library"
        const val AndroidDfm = "com.android.dynamic-feature"
    }
}
