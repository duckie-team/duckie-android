package land.sungbin.androidprojecttemplate.ui.login

import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.domain.repository.AuthRepository
import land.sungbin.androidprojecttemplate.util.UserHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userHolder: UserHolder,
) : BaseViewModel<LoginState, LoginSideEffect>(LoginState.Initial) {

    suspend fun kakaoLogin() = runCatching {
        postSideEffect { LoginSideEffect.NavigateToOnboard }
        //authRepository.kakaoLogin()
    }.onSuccess { response ->
        //login()
    }.onFailure {

    }

    private suspend fun login() = runCatching {
        authRepository.login()
    }.onSuccess { response ->
        userHolder.setUser(response.user)
    }.onFailure {
        updateState { LoginState.LoginFailed(it) }
    }

    private suspend fun signUp() = runCatching {
        authRepository.signUp()
    }.onSuccess {

    }.onFailure {

    }
}