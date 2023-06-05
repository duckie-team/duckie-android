/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.common.compose.ui.constant.SharedIcon

data class ProfileEditState(
    val isLoading: Boolean = true,
    val profile: Any? = SharedIcon.ic_default_profile,
    val nickname: String = "",
    val editEnabled: Boolean = false,
    val nicknameState: NicknameState = NicknameState.Checking,
    val galleryState: GalleryState = GalleryState(),
    val introduce: String = "",
    val introduceFocused: Boolean = false,
    val userId: Int = 0,
)

data class GalleryState(
    val visible: Boolean = false,
    val images: ImmutableList<String> = persistentListOf(),
    val imagesSelections: ImmutableList<Boolean> = persistentListOf(),
    val selectedIndex: Int = -1,
)

enum class NicknameState {
    Valid,
    Checking,
    NicknameRuleError,
    NicknameDuplicateError,
    ;

    fun isInValid(): Boolean {
        return this == NicknameRuleError || this == NicknameDuplicateError
    }
}
