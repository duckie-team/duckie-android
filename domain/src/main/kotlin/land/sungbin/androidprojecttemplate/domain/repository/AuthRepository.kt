package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.KakaoLoginResponse
import land.sungbin.androidprojecttemplate.domain.model.LoginResponse
import land.sungbin.androidprojecttemplate.domain.model.SignUpResponse

interface AuthRepository {
    suspend fun kakaoLogin(): KakaoLoginResponse
    suspend fun login(): LoginResponse
    suspend fun signUp(): SignUpResponse
}