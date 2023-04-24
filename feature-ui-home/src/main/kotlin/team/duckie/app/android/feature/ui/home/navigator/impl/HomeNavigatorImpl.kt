/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.ui.home.screen.MainActivity
import team.duckie.app.android.navigator.feature.home.HomeNavigator
import team.duckie.app.android.util.ui.startActivityWithAnimation
import javax.inject.Inject

internal class HomeNavigatorImpl @Inject constructor() : HomeNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<MainActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
