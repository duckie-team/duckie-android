package land.sungbin.androidprojecttemplate.data.repository.auth

import land.sungbin.androidprojecttemplate.data.domain.response.auth.KakaoLoginResponse
import land.sungbin.androidprojecttemplate.data.domain.response.auth.LoginResponse
import land.sungbin.androidprojecttemplate.data.domain.response.auth.SignUpResponse

interface AuthRepository {
    suspend fun kakaoLogin(): KakaoLoginResponse
    suspend fun login(): LoginResponse
    suspend fun signUp(): SignUpResponse
}