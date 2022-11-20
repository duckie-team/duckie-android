package team.duckie.app.android.ui.splash

import team.duckie.app.base.BaseViewModel
import team.duckie.app.domain.usecase.user.HasSessionUseCase
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
