package land.sungbin.androidprojecttemplate.ui.login

sealed class LoginState {
    object Initial : LoginState()
    data class KakaoLoginFailed(val exception: Throwable) : LoginState()
    data class LoginFailed(val exception: Throwable) : LoginState()
}

sealed class LoginSideEffect {
    object NavigateToMain : LoginSideEffect()
    object NavigateToOnboard : LoginSideEffect()
}