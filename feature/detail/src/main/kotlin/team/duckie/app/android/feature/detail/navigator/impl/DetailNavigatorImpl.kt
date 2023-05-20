/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.detail.DetailActivity
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.util.ui.startActivityWithAnimation
import javax.inject.Inject

internal class DetailNavigatorImpl @Inject constructor() : DetailNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<DetailActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
