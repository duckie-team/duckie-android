/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.friends.navigator.impl

import android.app.Activity
import android.content.Intent
import team.duckie.app.android.feature.friends.FriendsActivity
import team.duckie.app.android.navigator.feature.friend.FriendNavigator
import team.duckie.app.android.common.android.ui.startActivityWithAnimation
import javax.inject.Inject

internal class FriendNavigatorImpl @Inject constructor() : FriendNavigator {
    override fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean,
    ) {
        activity.startActivityWithAnimation<FriendsActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish,
        )
    }
}
