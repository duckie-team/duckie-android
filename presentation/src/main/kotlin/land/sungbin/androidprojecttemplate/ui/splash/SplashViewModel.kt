package land.sungbin.androidprojecttemplate.ui.splash

import land.sungbin.androidprojecttemplate.base.BaseViewModel

class SplashViewModel : BaseViewModel<SplashPage, SplashSideEffect>(SplashPage.First) {

    fun navigatePage(page: SplashPage) = updateState { page }
}