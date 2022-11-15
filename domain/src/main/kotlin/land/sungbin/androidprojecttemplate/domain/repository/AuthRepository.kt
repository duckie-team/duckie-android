package land.sungbin.androidprojecttemplate.domain.repository

interface AuthRepository {
    suspend fun kakaoLogin(): KakaoLoginResponse
    suspend fun login(): LoginResponse
    suspend fun signUp(): SignUpResponse
}