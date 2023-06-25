/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.navigator.feature.profile

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import team.duckie.app.android.navigator.base.Navigator

interface ProfileNavigator : Navigator {
    fun activityResultLaunch(
        launcher: ActivityResultLauncher<Intent>,
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
    )
}
