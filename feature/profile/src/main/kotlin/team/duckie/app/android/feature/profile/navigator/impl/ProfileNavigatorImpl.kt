/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.navigator.impl

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import team.duckie.app.android.feature.profile.ProfileActivity
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import team.duckie.app.android.common.android.ui.startLauncherWithAnimation
import javax.inject.Inject

internal class ProfileNavigatorImpl @Inject constructor() : ProfileNavigator {
    override fun activityResultLaunch(
        launcher: ActivityResultLauncher<Intent>,
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
    ) {
        activity.startLauncherWithAnimation<ProfileActivity>(
            launcher = launcher,
            intentBuilder = intentBuilder,
        )
    }

    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<ProfileActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
