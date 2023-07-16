/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.kakao.repository

import android.content.Context
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.android.common.kotlin.exception.DuckieThirdPartyException
import team.duckie.app.android.common.kotlin.exception.ExceptionCode
import team.duckie.app.android.domain.kakao.repository.KakaoRepository
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume

private val KakaoLoginException = IllegalStateException("Kakao API response is nothing.")

private val KakaoTalkNotSupportException = DuckieThirdPartyException(
    code = ExceptionCode.KAKAOTALK_NOT_SUPPORT_EXCEPTION,
)

private const val KakaoNotSupportStatusCode: Int = 302

// Calling startActivity() from outside of an Activity.
// Activity Context 필요.
class KakaoRepositoryImpl @Inject constructor(
    @ActivityContext private val activityContext: Context,
) : KakaoRepository {
    private val _activity = WeakReference(activityContext)
    private val activity get() = _activity.get()!!

    override suspend fun getAccessToken(): String {
        return if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
            loginWithKakaoTalk()
        } else {
            loginWithWebView()
        }
    }

    private suspend fun loginWithKakaoTalk(): String {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
                continuation.resume(
                    when {
                        error != null -> {
                            when (error) {
                                is AuthError -> {
                                    if (error.statusCode == KakaoNotSupportStatusCode) {
                                        failure(KakaoTalkNotSupportException)
                                    } else {
                                        failure(error)
                                    }
                                }

                                else -> failure(error)
                            }
                        }

                        token != null -> success(token.accessToken)
                        else -> failure(KakaoLoginException)
                    },
                )
            }
        }.getOrThrow()
    }

    override suspend fun loginWithWebView(): String {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(activity) { token, error ->
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        token != null -> success(token.accessToken)
                        else -> failure(KakaoLoginException)
                    },
                )
            }
        }.getOrThrow()
    }
}
