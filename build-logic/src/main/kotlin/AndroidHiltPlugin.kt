/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.kapts
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import team.duckie.app.android.convention.PluginEnum
import team.duckie.app.android.convention.applyPlugins
import team.duckie.app.android.convention.libs

/**
 * Android 프레임워크에 의존적인 환경에서 Hilt 를 사용할 준비를 합니다.
 */
internal class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.KotlinKapt,
                libs.findPlugin("di-hilt").get().get().pluginId,
            )

            dependencies {
                implementations(libs.findLibrary("di-hilt-core").get())
                kapts(libs.findLibrary("di-hilt-compiler").get())
            }
        }
    }
}
