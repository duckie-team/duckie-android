/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.viewmodel.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.exam.model.IgnoreExam
import team.duckie.app.android.domain.user.model.IgnoreUser
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.setting.constans.SettingType
import team.duckie.app.android.feature.setting.constans.Withdraweason

@Immutable
internal data class SettingState(
    val me: User? = null,
    val isStage: Boolean = false,
    val settingType: SettingType = SettingType.Main,

    val devModeDialogVisible: Boolean = false,
    val logoutDialogVisible: Boolean = false,
    val withdrawDialogVisible: Boolean = false,

    // 회원탈퇴 관련
    val withdrawReasonSelected: Withdraweason = Withdraweason.INITIAL,
    val withdrawUserInputReason: String = "",
    val withdrawIsFocused: Boolean = false,

    val ignoreUsers: ImmutableList<IgnoreUser> = persistentListOf(),
    val ignoreExams: ImmutableList<IgnoreExam> = persistentListOf(),
)
