package land.sungbin.androidprojecttemplate.ui.login

import land.sungbin.androidprojecttemplate.base.SideEffect
import land.sungbin.androidprojecttemplate.base.State

sealed class LoginState : State {
    object Initial : LoginState()
    data class KakaoLoginFailed(val exception: Throwable) : LoginState()
    data class LoginFailed(val exception: Throwable) : LoginState()
}

sealed class LoginSideEffect : SideEffect {
    object NavigateToMain : LoginSideEffect()
    object NavigateToOnboard : LoginSideEffect()
}