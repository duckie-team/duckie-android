/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import team.duckie.app.android.convention.androidTestImplementations
import team.duckie.app.android.convention.debugImplementations
import team.duckie.app.android.convention.libs

/**
 * Android 프레임워크의 Library 환경에서 Jetpack Compose 의 UI 테스트를 진행할 준비를 합니다.
 */
internal class AndroidLibraryComposeUiTestPlugin : Plugin<Project> {
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            val extension = extensions.getByType<LibraryExtension>()
            extension.apply {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                debugImplementations(
                    libs.findLibrary("test-compose-manifest").get(),
                )
                androidTestImplementations(
                    libs.findLibrary("test-compose-junit").get(),
                )
            }
        }
    }
}

