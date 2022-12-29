/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.auth.usecase

import javax.inject.Inject
import team.duckie.app.android.domain.auth.repository.AuthRepository

class CheckAccessTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend fun invoke(): Result<Int> { // 유저 아이디 반환
        return runCatching {
            repository.checkAccessToken().userId
        }
    }
}
