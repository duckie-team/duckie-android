/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import team.duckie.app.android.convention.ApplicationConstants

class AppVersionNameProvider : Plugin<Project> {
    override fun apply(target: Project) = Unit

    companion object App {
        const val VersionName = ApplicationConstants.versionName
        const val VersionCode = ApplicationConstants.versionCode
    }
}
