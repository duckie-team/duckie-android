/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.ui.search.screen.SearchActivity
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import team.duckie.app.android.util.ui.startActivityWithAnimation
import javax.inject.Inject

internal class SearchNavigatorImpl @Inject constructor() : SearchNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<SearchActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
