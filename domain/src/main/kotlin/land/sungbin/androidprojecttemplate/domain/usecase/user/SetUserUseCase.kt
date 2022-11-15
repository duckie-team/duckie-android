package land.sungbin.androidprojecttemplate.domain.usecase.user

import land.sungbin.androidprojecttemplate.domain.model.LoginUser
import land.sungbin.androidprojecttemplate.domain.repository.UserRepository

class SetUserUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(user: LoginUser) = runCatching {
        repository.setUser(user)
    }
}