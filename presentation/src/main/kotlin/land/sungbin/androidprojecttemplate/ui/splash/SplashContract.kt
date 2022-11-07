package land.sungbin.androidprojecttemplate.ui.splash

import land.sungbin.androidprojecttemplate.base.ViewSideEffect
import land.sungbin.androidprojecttemplate.base.ViewState

enum class SplashPage : ViewState {
    First,
    Second,
}

sealed class SplashSideEffect: ViewSideEffect {
    object NavigateToMain : SplashSideEffect()
    object NavigateToLogin : SplashSideEffect()
}