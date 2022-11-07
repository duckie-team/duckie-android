package land.sungbin.androidprojecttemplate.ui.splash

import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.util.UserHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashViewModel @Inject constructor(
    private val userHolder: UserHolder,
) : BaseViewModel<SplashPage, SplashSideEffect>(SplashPage.First) {

    fun navigatePage(page: SplashPage) = updateState { page }

    suspend fun onCheckSession() {
        when (userHolder.hasSession()) {
            true -> postSideEffect { SplashSideEffect.NavigateToMain }
            else -> postSideEffect { SplashSideEffect.NavigateToLogin }
        }
    }
}