/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.navigator.impl

import android.content.Context
import android.content.Intent
import team.duckie.app.android.feature.ui.home.screen.HomeActivity
import team.duckie.app.android.navigator.feature.home.HomeNavigator
import javax.inject.Inject

internal class HomeNavigatorImpl @Inject constructor() : HomeNavigator {
    override fun intent(context: Context): Intent {
        return Intent(context, HomeActivity::class.java)
    }
}
