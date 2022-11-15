package land.sungbin.androidprojecttemplate.domain.usecase.user

import land.sungbin.androidprojecttemplate.domain.repository.UserRepository

class HasSessionUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke() = runCatching {
        repository.hasSession()
    }
}