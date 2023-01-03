/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.auth.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.auth.model.JoinResponse
import team.duckie.app.android.domain.auth.repository.AuthRepository

@Immutable
class JoinUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(kakaoOAuthToken: String): Result<JoinResponse> {
        return runCatching {
            repository.join(kakaoOAuthToken)
        }
    }
}
