/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.viewmodel.state

import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.setting.constans.SettingType
import team.duckie.app.android.feature.setting.constans.Withdraweason

internal data class SettingState(
    val me: User? = null,
    val settingType: SettingType = SettingType.Main,

    val devModeDialogVisible: Boolean = false,
    val logoutDialogVisible: Boolean = false,
    val withdrawDialogVisible: Boolean = false,

    // 회원탈퇴 관련
    val withdrawReasonSelected: Withdraweason = Withdraweason.INITIAL,
    val withdrawUserInputReason: String = "",
    val withdrawIsFocused: Boolean = false,
)
