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
import team.duckie.app.android.util.kotlin.DuckieResponseException

private const val ValidationFaildErrorCode = "TOKEN_EXPIRED"

@Immutable
class CheckAccessTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    @Suppress("TooGenericExceptionCaught")
    suspend operator fun invoke(token: String): Result<Boolean> {
        return try {
            repository.checkAccessToken(token)
            Result.success(true)
        } catch (exception: Exception) {
            val validationError = exception as? DuckieResponseException
            if (validationError?.code == ValidationFaildErrorCode) {
                Result.success(false)
            } else {
                Result.failure(exception)
            }
        }
    }
}
