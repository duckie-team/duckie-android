package land.sungbin.androidprojecttemplate.data.datasource.remote

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject

class KakaoDatasource(
    @Inject private val client: UserApiClient,
) {
    fun login(
        context: Context,
        onLoginSuccess: (OAuthToken) -> Unit,
        onLoginFailed: (Throwable) -> Unit,
    ) {
        client.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                onLoginFailed(error)
            } else if (token != null) {
                onLoginSuccess(token)
            }
        }
    }
}