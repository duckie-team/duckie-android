/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
package team.duckie.app.android.feature.skeleton.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import team.duckie.app.android.feature.skeleton.SkeletonActivity
import team.duckie.app.android.navigator.feature.skeleton.SkeletonNavigator
import javax.inject.Inject

internal class SkeletonNavigatorImpl @Inject constructor() : SkeletonNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<SkeletonActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
