package team.duckie.app.android.domain.usecase.user

import team.duckie.app.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke() = runCatching {
        repository.getUser()
    }
}
