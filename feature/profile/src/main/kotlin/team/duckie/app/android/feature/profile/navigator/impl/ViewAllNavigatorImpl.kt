/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.profile.ViewAllActivity
import team.duckie.app.android.navigator.feature.profile.ViewAllNavigator
import javax.inject.Inject

class ViewAllNavigatorImpl @Inject constructor() : ViewAllNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivity(Intent(activity, ViewAllActivity::class.java).intentBuilder())
    }
}
