package land.sungbin.androidprojecttemplate.ui.splash

import land.sungbin.androidprojecttemplate.base.SideEffect
import land.sungbin.androidprojecttemplate.base.State

data class SplashState(
    val splashViewState: SplashPage = SplashPage.First,
) : State

enum class SplashPage {
    First,
    Second
}

sealed class SplashSideEffect : SideEffect {
    object NavigateToMain : SplashSideEffect()
    object NavigateToLogin : SplashSideEffect()
}