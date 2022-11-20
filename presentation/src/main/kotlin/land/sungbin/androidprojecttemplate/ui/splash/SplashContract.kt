package land.sungbin.androidprojecttemplate.ui.splash
data class SplashState(
    val splashViewState: SplashPage = SplashPage.First,
)

enum class SplashPage {
    First,
    Second
}

sealed class SplashSideEffect{
    object NavigateToMain : SplashSideEffect()
    object NavigateToLogin : SplashSideEffect()
}