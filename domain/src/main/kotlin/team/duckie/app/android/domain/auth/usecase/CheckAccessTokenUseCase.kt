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
import team.duckie.app.android.util.kotlin.exception.ExceptionCode

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
            // TODO(sungbin): responseCatching 재구현 후 exception.kt 에 정리할 것
            val isValidationFailedError = exception.toString().run {
                contains(ExceptionCode.TOKEN_EXPIRED) || contains(ExceptionCode.USER_NOT_FOUND)
            }
            if (isValidationFailedError) {
                Result.success(AccessTokenValidation.failure)
            } else {
                Result.failure(exception)
            }
        }
    }
}
