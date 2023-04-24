/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.state

import android.os.Parcelable
import java.io.File
import kotlinx.parcelize.Parcelize
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep

@Parcelize
internal data class OnboardState(
    val step: OnboardStep = OnboardStep.Activity,
    val me: User? = null,
    val finishOnboarding: Boolean = false,
    val profileState: ProfileScreenState = ProfileScreenState.Checking,
    val temporaryNickname: String? = null,
    val temporaryProfileImageFile: File? = null,
    val galleryImages: List<String> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategories: List<Category> = emptyList(),
    val isNewUser: Boolean = false,
) : Parcelable

/** ProfileScreen 의 state */
enum class ProfileScreenState {
    Valid,
    Checking,
    NicknameRuleError,
    NicknameDuplicateError,
}
