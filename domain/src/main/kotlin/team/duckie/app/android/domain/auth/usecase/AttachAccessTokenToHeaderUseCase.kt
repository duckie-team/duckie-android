/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.auth.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.auth.repository.AuthRepository

@Immutable
class AttachAccessTokenToHeaderUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke(accessToken: String): Result<Unit> {
        return runCatching {
            repository.attachAccessTokenToHeader(accessToken)
        }
    }
}
