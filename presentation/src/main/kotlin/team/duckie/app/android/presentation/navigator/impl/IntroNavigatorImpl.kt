/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.navigator.feature.intro.IntroNavigator
import team.duckie.app.android.presentation.IntroActivity
import team.duckie.app.android.util.ui.startActivityWithAnimation
import javax.inject.Inject

internal class IntroNavigatorImpl @Inject constructor() : IntroNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<IntroActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
