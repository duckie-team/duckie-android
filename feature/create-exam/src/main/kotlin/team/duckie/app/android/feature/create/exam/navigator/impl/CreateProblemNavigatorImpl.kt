/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.create.exam.CreateExamActivity
import team.duckie.app.android.navigator.feature.createproblem.CreateProblemNavigator
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import javax.inject.Inject

internal class CreateProblemNavigatorImpl @Inject constructor() : CreateProblemNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<CreateExamActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
