/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.setting.screen.SettingActivity
import team.duckie.app.android.navigator.feature.setting.SettingNavigator
import team.duckie.app.android.util.ui.startActivityWithAnimation
import javax.inject.Inject

internal class SettingNavigatorImpl @Inject constructor() : SettingNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<SettingActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
