package land.sungbin.androidprojecttemplate.data.repository.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import land.sungbin.androidprojecttemplate.data.domain.User
import land.sungbin.androidprojecttemplate.data.domain.response.auth.KakaoLoginResponse
import land.sungbin.androidprojecttemplate.data.domain.response.auth.LoginResponse
import land.sungbin.androidprojecttemplate.data.domain.response.auth.SignUpResponse
import land.sungbin.androidprojecttemplate.util.KakaoUtil
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : AuthRepository {

    override suspend fun kakaoLogin(): KakaoLoginResponse {
        return suspendCoroutine { coroutine ->
            KakaoUtil.login(
                context = context,
                onLoginSuccess = {
                    coroutine.resume(KakaoLoginResponse(it))
                },
                onLoginFailed = {
                    coroutine.resumeWithException(it)
                }
            )
        }
    }
    override suspend fun login(): LoginResponse {
        return LoginResponse(User())
    }

    override suspend fun signUp(): SignUpResponse {
        TODO("Not yet implemented")
    }
}