/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.tag.edit.navigator.impl

import team.duckie.app.android.feature.tag.edit.TagEditActivity
import android.app.Activity
import android.content.Intent
import team.duckie.app.android.navigator.feature.tagedit.TagEditNavigator
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import javax.inject.Inject

internal class TagEditNavigatorImpl @Inject constructor() : TagEditNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<TagEditActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
