/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import team.duckie.app.android.convention.libs
import team.duckie.app.android.convention.setupJunit
import team.duckie.app.android.convention.testImplementations

/**
 * Android 프레임워크에 의존적이지 않은 순수한 Junit4 테스트 환경을 구성합니다.
 */
internal class JvmJUnit4Plugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                setupJunit(
                    core = libs.findLibrary("test-junit-core").get(),
                    engine = libs.findLibrary("test-junit-engine").get(),
                )
                testImplementations(libs.findLibrary("test-strikt").get())
            }
        }
    }
}
