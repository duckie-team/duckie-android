package team.duckie.app.android.domain.usecase.auth

import team.duckie.app.domain.repository.AuthRepository

class KakaoLoginUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.kakaoLogin()
    }
}
