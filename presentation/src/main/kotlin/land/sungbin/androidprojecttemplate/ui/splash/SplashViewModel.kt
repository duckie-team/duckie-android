package land.sungbin.androidprojecttemplate.ui.splash

import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.util.UserHolder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashViewModel @Inject constructor(
    private val userHolder: UserHolder,
) : BaseViewModel<SplashState, SplashSideEffect>(SplashState()) {

    fun navigatePage(page: SplashPage) = updateState {
        copy(splashViewState = page)
    }

    suspend fun onCheckSession() {
        when (userHolder.hasSession()) {
            true -> postSideEffect { SplashSideEffect.NavigateToMain }
            else -> postSideEffect { SplashSideEffect.NavigateToLogin }
        }
    }
}