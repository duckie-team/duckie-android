package team.duckie.app.android.domain.usecase.auth

import team.duckie.app.domain.repository.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.signUp()
    }
}
