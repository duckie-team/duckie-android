package land.sungbin.androidprojecttemplate.ui.splash

import land.sungbin.androidprojecttemplate.base.ViewSideEffect
import land.sungbin.androidprojecttemplate.base.ViewState

data class SplashState(
    val splashViewState: SplashPage = SplashPage.First,
) : ViewState

enum class SplashPage {
    First,
    Second
}

sealed class SplashSideEffect : ViewSideEffect {
    object NavigateToMain : SplashSideEffect()
    object NavigateToLogin : SplashSideEffect()
}