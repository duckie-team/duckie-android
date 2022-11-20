package team.duckie.app.android.domain.repository

import team.duckie.app.domain.model.KakaoLoginResponse
import team.duckie.app.domain.model.LoginResponse
import team.duckie.app.domain.model.SignUpResponse

interface AuthRepository {
    suspend fun kakaoLogin(): KakaoLoginResponse
    suspend fun login(): LoginResponse
    suspend fun signUp(): SignUpResponse
}
