package land.sungbin.androidprojecttemplate.domain.usecase.auth

import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = runCatching {
        repository.login()
    }
}