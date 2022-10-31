package land.sungbin.androidprojecttemplate.util

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

object KakaoUtil {

    fun login(
        context: Context,
        onLoginSuccess: (OAuthToken) -> Unit,
        onLoginFailed: (Throwable) -> Unit
    ) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                onLoginFailed(error)
            } else if (token != null) {
                onLoginSuccess(token)
            }
        }
    }
}