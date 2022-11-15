package land.sungbin.androidprojecttemplate.ui.login

import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.domain.usecase.auth.KakaoLoginUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.auth.LoginUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.auth.SignUpUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.user.SetUserUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val setUserUseCase: SetUserUseCase,
) : BaseViewModel<LoginState, LoginSideEffect>(LoginState.Initial) {

    suspend fun kakaoLogin() {
        postSideEffect { LoginSideEffect.NavigateToOnboard }
        kakaoLoginUseCase().onSuccess {
            //login()
        }.onFailure {

        }

    }

    private suspend fun login() {
        loginUseCase().onSuccess { response ->
            setUserUseCase(response.user).onSuccess {

            }.onFailure {

            }
        }.onFailure {
            updateState { LoginState.LoginFailed(it) }
        }
    }

    private suspend fun signUp() {
        signUpUseCase().onSuccess {

        }.onFailure {

        }
    }
}