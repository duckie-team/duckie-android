package land.sungbin.androidprojecttemplate.ui.splash

import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.domain.usecase.user.HasSessionUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashViewModel @Inject constructor(
    private val hasSessionUseCase: HasSessionUseCase,
) : BaseViewModel<SplashState, SplashSideEffect>(SplashState()) {

    fun navigatePage(page: SplashPage) = updateState {
        copy(splashViewState = page)
    }

    suspend fun onCheckSession() {
        hasSessionUseCase().onSuccess {
            postSideEffect { SplashSideEffect.NavigateToMain }
        }.onFailure {
            postSideEffect { SplashSideEffect.NavigateToLogin }
        }
    }
}