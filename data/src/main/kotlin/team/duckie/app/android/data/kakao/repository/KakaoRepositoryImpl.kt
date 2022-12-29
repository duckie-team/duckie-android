/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.kakao.repository

import android.app.Activity
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import java.lang.ref.WeakReference
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlin.random.Random
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.android.data.BuildConfig
import team.duckie.app.android.data.kakao.mapper.toKakaoUser
import team.duckie.app.android.domain.kakao.model.KakaoUser
import team.duckie.app.android.domain.kakao.repository.KakaoRepository

private val KakaoLoginException = IllegalStateException("Kakao API response is nothing.")
private val DefaultUserNickname = "익명의 덕키즈 #${Random.nextInt(10_000)}"

// Calling startActivity() from outside of an Activity.
// Activity Context 필요.
class KakaoRepositoryImpl(activityContext: Activity) : KakaoRepository {
    private val _activity = WeakReference(activityContext)
    private val activity get() = _activity.get()!!

    init {
        KakaoSdk.init(activity, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    override suspend fun login(): KakaoUser {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
            loginWithKakaoTalk()
        } else {
            loginWithWebView()
        }
        return fetchKakaoUser()
    }

    private suspend fun loginWithKakaoTalk() {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        token != null -> success(Unit)
                        else -> failure(KakaoLoginException)
                    }
                )
            }
        }.getOrThrow()
    }

    private suspend fun loginWithWebView() {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(activity) { token, error ->
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        token != null -> success(Unit)
                        else -> failure(KakaoLoginException)
                    }
                )
            }
        }.getOrThrow()
    }

    private suspend fun fetchKakaoUser(): KakaoUser {
        return suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        user != null -> success(user.toKakaoUser(defaultName = DefaultUserNickname))
                        else -> failure(KakaoLoginException)
                    }
                )
            }
        }.getOrThrow()
    }
}
