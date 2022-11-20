package team.duckie.app.android.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.data.datasource.remote.KakaoDatasource
import team.duckie.app.data.mapper.toDoMain
import team.duckie.app.data.mapper.toDomain
import team.duckie.app.data.model.auth.KakaoLoginResponseData
import team.duckie.app.data.model.auth.LoginResponseData
import team.duckie.app.data.model.auth.LoginUserData
import team.duckie.app.data.model.auth.SignUpResponseData
import team.duckie.app.domain.model.KakaoLoginResponse
import team.duckie.app.domain.model.LoginResponse
import team.duckie.app.domain.model.SignUpResponse
import team.duckie.app.domain.repository.AuthRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val kaKaoDatasource: KakaoDatasource = KakaoDatasource(),
) : AuthRepository {

    override suspend fun kakaoLogin(): KakaoLoginResponse {
        return suspendCancellableCoroutine { coroutine ->
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
