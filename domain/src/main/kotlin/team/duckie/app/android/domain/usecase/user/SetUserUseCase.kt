package team.duckie.app.android.domain.usecase.user

import team.duckie.app.domain.model.LoginUser
import team.duckie.app.domain.repository.UserRepository

class SetUserUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(user: LoginUser) = runCatching {
        repository.setUser(user)
    }
}
