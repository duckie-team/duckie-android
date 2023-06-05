/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.notification.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.notification.NotificationActivity
import team.duckie.app.android.navigator.feature.notification.NotificationNavigator
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import javax.inject.Inject

class NotificationNavigatorImpl @Inject constructor() : NotificationNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<NotificationActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
