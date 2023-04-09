/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel.state

data class ProfileEditState(
    val profile: Any? = null,
    val nickName: String = "",
    val profileState: ProfileScreenState = ProfileScreenState.Checking,
)

/** ProfileScreen 의 state */
enum class ProfileScreenState {
    Valid,
    Checking,
    NicknameRuleError,
    NicknameDuplicateError,
}
