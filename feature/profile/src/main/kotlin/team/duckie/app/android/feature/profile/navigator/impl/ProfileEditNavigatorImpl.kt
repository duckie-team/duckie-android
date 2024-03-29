/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.profile.screen.edit.ProfileEditActivity
import team.duckie.app.android.navigator.feature.profile.ProfileEditNavigator
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import javax.inject.Inject

internal class ProfileEditNavigatorImpl @Inject constructor() : ProfileEditNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<ProfileEditActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
