package land.sungbin.androidprojecttemplate.domain.usecase.auth

import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository

class KakaoLoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = runCatching {
        repository.kakaoLogin()
    }
}