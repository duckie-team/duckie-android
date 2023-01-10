/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("UnstableApiUsage", "UnusedReceiverParameter")

package team.duckie.app.android.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

/**
 * 기본 Application 그레이들 환경으로 설정합니다.
 *
 * @param extension 설정할 그레이들의 [CommonExtension]
 */
internal fun Project.configureApplication(extension: CommonExtension<*, *, *, *>) {
    extension.apply {
        compileSdk = ApplicationConstants.compileSdk

        defaultConfig {
            minSdk = ApplicationConstants.minSdk
        }

        sourceSets {
            getByName("main").java.srcDirs("src/main/kotlin/")
        }

        compileOptions {
            sourceCompatibility = ApplicationConstants.javaVersion
            targetCompatibility = ApplicationConstants.javaVersion
        }

        lint {
            checkTestSources = true
        }

        dependencies.add("detektPlugins", libs.findLibrary("detekt-plugin-formatting").get().get())
    }
}
