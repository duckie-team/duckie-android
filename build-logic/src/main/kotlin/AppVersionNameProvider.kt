/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import team.duckie.app.android.convention.ApplicationConstants
import java.io.FileInputStream
import java.util.Properties

class AppVersionNameProvider : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.withType(AppPlugin::class.java) {

            // build.gradle.kts 가 동작한 뒤 실행됨
            project.afterEvaluate {
                // app 모듈만 캐치함
                val android = project.extensions.getByType(AppExtension::class)

                // productFlavor 설정된 내용을 순회
                android.productFlavors.all { flavor ->
                    // 각 환견 API URL 정보를 가져오기 위해, local.properties 내용 가져오기
                    val configFile = project.rootProject.file("local.properties")
                    val properties = Properties()
                    properties.load(FileInputStream(configFile))

                    // local.properties 공통 작업
                    stageUrl = properties["STAGE_BASE_URL"] as String
                    realUrl = properties["BASE_URL"] as String
                    devModePassword = properties["DEV_MODE_PASSWORD"] as String
                    return@all true
                }
            }
        }
    }

    companion object App {
        const val VersionName = ApplicationConstants.versionName
        const val VersionCode = ApplicationConstants.versionCode

        var stageUrl = ""
        var realUrl = ""
        var devModePassword = ""
    }
}
