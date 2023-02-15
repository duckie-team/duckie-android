/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.ui.exam.result.ExamResultActivity
import team.duckie.app.android.navigator.feature.examresult.ExamResultNavigator
import team.duckie.app.android.util.ui.startActivityWithAnimation
import javax.inject.Inject

internal class ExamResultNavigatorImpl @Inject constructor() : ExamResultNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean
    ) {
        activity.startActivityWithAnimation<ExamResultActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish
        )
    }
}
