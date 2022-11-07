package land.sungbin.androidprojecttemplate.ui.login

import land.sungbin.androidprojecttemplate.base.ViewSideEffect
import land.sungbin.androidprojecttemplate.base.ViewState

sealed class LoginState : ViewState {
    object Initial : LoginState()
    data class KakaoLoginFailed(val exception: Throwable) : LoginState()
    data class LoginFailed(val exception: Throwable) : LoginState()
}

sealed class LoginSideEffect : ViewSideEffect {
    object NavigateToMain : LoginSideEffect()
    object NavigateToOnboard : LoginSideEffect()
}