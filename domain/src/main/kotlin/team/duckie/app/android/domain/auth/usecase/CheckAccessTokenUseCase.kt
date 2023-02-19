/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("ConstPropertyName")

package team.duckie.app.android.domain.auth.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.auth.model.AccessTokenValidation
import team.duckie.app.android.domain.auth.repository.AuthRepository

private const val ValidationFaildErrorCode = "TOKEN_EXPIRED"

@Immutable
class CheckAccessTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    @Suppress("TooGenericExceptionCaught")
    suspend operator fun invoke(token: String): Result<AccessTokenValidation> {
        return try {
            val userId = repository.checkAccessToken(token).userId
            Result.success(AccessTokenValidation.fromUserId(userId))
        } catch (exception: Exception) {
            // TODO(sungbin): responseCatching 재구현 후 로직 복구
            /*val validationError = exception as? DuckieResponseException
            if (validationError?.code == ValidationFaildErrorCode) {
                Result.success(false)
            } else {
                Result.failure(exception)
            }*/
            // TODO(sungbin): USER_NOT_FOUND 하드코딩 제거
            // TODO(sungbin): USER_NOT_FOUND 일 때 UI 대응
            val isValidationFailedError = exception.toString().run {
                contains(ValidationFaildErrorCode) || contains("USER_NOT_FOUND")
            }
            if (isValidationFailedError) {
                Result.success(AccessTokenValidation.failure)
            } else {
                Result.failure(exception)
            }
        }
    }
}
