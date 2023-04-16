/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.kakao.repository

import android.app.Activity
import com.kakao.sdk.user.UserApiClient
import java.lang.ref.WeakReference
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.android.domain.kakao.repository.KakaoRepository

private val KakaoLoginException = IllegalStateException("Kakao API response is nothing.")

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
                        error != null -> failure(error)
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
