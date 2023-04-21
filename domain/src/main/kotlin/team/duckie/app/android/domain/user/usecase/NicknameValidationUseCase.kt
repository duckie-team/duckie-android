/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.user.usecase

import javax.inject.Inject

class NicknameValidationUseCase @Inject constructor() {
    private val nicknameFilter = Regex("[^ㄱ-ㅎ가-힣a-zA-Z0-9_.]")

    operator fun invoke(nickname: String) = nicknameFilter.containsMatchIn(nickname)
}
