/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
)

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import team.duckie.app.android.convention.ApplicationConstants
import team.duckie.app.android.convention.PluginEnum
import team.duckie.app.android.convention.applyPlugins
import team.duckie.app.android.convention.configureApplication

/**
 * Android 프레임워크의 Application 환경을 구성합니다.
 */
internal class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.AndroidApplication,
                PluginEnum.KotlinAndroid,
            )

            extensions.configure<BaseAppModuleExtension> {
                configureApplication(this)

                defaultConfig {
                    targetSdk = ApplicationConstants.targetSdk
                    this.versionName = ApplicationConstants.versionName
                    this.versionCode = ApplicationConstants.versionCode
                }
            }
        }
    }
}
