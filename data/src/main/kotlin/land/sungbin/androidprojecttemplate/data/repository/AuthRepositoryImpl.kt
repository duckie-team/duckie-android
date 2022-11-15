package land.sungbin.androidprojecttemplate.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import land.sungbin.androidprojecttemplate.data.datasource.remote.KakaoDatasource
import land.sungbin.androidprojecttemplate.data.model.auth.KakaoLoginResponse
import land.sungbin.androidprojecttemplate.data.model.auth.LoginResponse
import land.sungbin.androidprojecttemplate.data.model.auth.SignUpResponse
import land.sungbin.androidprojecttemplate.data.model.auth.User
import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @Inject val kaKaoDatasource: KakaoDatasource,
) : AuthRepository {

    override suspend fun kakaoLogin(): KakaoLoginResponse {
        return suspendCoroutine { coroutine ->
            kaKaoDatasource.login(
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