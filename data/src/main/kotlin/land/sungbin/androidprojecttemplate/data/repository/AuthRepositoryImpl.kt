package land.sungbin.androidprojecttemplate.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import land.sungbin.androidprojecttemplate.data.datasource.remote.KakaoDatasource
import land.sungbin.androidprojecttemplate.data.mapper.toDoMain
import land.sungbin.androidprojecttemplate.data.mapper.toDomain
import land.sungbin.androidprojecttemplate.data.model.auth.KakaoLoginResponseData
import land.sungbin.androidprojecttemplate.data.model.auth.LoginResponseData
import land.sungbin.androidprojecttemplate.data.model.auth.LoginUserData
import land.sungbin.androidprojecttemplate.data.model.auth.SignUpResponseData
import land.sungbin.androidprojecttemplate.domain.model.KakaoLoginResponse
import land.sungbin.androidprojecttemplate.domain.model.LoginResponse
import land.sungbin.androidprojecttemplate.domain.model.SignUpResponse
import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val kaKaoDatasource: KakaoDatasource = KakaoDatasource(),
) : AuthRepository {

    override suspend fun kakaoLogin(): KakaoLoginResponse {
        return suspendCoroutine { coroutine ->
            kaKaoDatasource.login(
                context = context,
                onLoginSuccess = {
                    coroutine.resume(KakaoLoginResponseData(it).toDoMain())
                },
                onLoginFailed = {
                    coroutine.resumeWithException(it)
                }
            )
        }
    }

    override suspend fun login(): LoginResponse {
        return LoginResponseData(LoginUserData()).toDomain()
    }

    override suspend fun signUp(): SignUpResponse {
        return SignUpResponseData(false).toDomain()
    }
}