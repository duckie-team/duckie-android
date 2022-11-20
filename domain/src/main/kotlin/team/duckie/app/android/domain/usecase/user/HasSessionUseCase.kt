package team.duckie.app.android.domain.usecase.user

import team.duckie.app.domain.repository.UserRepository

class HasSessionUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke() = runCatching {
        repository.hasSession()
    }
}
