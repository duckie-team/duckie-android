/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.kakao.repository

import android.app.Activity
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.android.domain.kakao.repository.KakaoRepository
import team.duckie.app.android.util.kotlin.exception.DuckieThirdPartyException
import team.duckie.app.android.util.kotlin.exception.ExceptionCode
import java.lang.ref.WeakReference
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume

private val KakaoLoginException = IllegalStateException("Kakao API response is nothing.")

private val KakaoTalkNotConnectedAccountException = DuckieThirdPartyException(
    message = "카카오톡이 이미 설치되어 있어요!\n카카오톡에서 로그인 후 다시 이용해 주세요.",
    code = ExceptionCode.KAKAOTALK_IS_INSTALLED_BUT_NOT_CONNECTED_ACCOUNT,
)

// Calling startActivity() from outside of an Activity.
// Activity Context 필요.
class KakaoRepositoryImpl(activityContext: Activity) : KakaoRepository {
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
                                is AuthError -> failure(KakaoTalkNotConnectedAccountException)
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

    private suspend fun loginWithWebView(): String {
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
