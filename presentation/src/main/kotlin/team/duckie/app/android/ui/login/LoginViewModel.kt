package team.duckie.app.android.ui.login

import team.duckie.app.base.BaseViewModel
import team.duckie.app.domain.usecase.auth.KakaoLoginUseCase
import team.duckie.app.domain.usecase.auth.LoginUseCase
import team.duckie.app.domain.usecase.auth.SignUpUseCase
import team.duckie.app.domain.usecase.user.SetUserUseCase
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
        }.onFailure { t: Throwable ->
            updateState { LoginState.LoginFailed(t) }
        }
    }

    private suspend fun signUp() {
        signUpUseCase().onSuccess {

        }.onFailure {

        }
    }
}
