/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.friends.viewmodel.sideeffect

internal sealed class FriendsSideEffect {
    class ReportError(val exception: Throwable) : FriendsSideEffect()

    class NavigateToUserProfile(val userId: Int) : FriendsSideEffect()
}
