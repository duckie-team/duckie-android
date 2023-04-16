/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.presentation.viewmodel.sideeffect.IntroSideEffect
import team.duckie.app.android.presentation.viewmodel.state.IntroState
import team.duckie.app.android.util.kotlin.exception.isAppUpgradeRequire
import team.duckie.app.android.util.kotlin.exception.isLoginRequireCode
import team.duckie.app.android.util.kotlin.exception.isTokenExpired
import team.duckie.app.android.util.kotlin.exception.isUserNotFound
import javax.inject.Inject

@HiltViewModel
internal class IntroViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMeUseCase: GetMeUseCase,
) : ContainerHost<IntroState, IntroSideEffect>, ViewModel() {

    override val container = container<IntroState, IntroSideEffect>(
        initialState = IntroState(),
        savedStateHandle = savedStateHandle,
    )

    /** 앱 업데이트 여부를 체크한다. */
    suspend fun checkUpdateRequire() = intent {
        getMeUseCase()
            .onSuccess { user ->
                postSideEffect(
                    IntroSideEffect.GetUserFinished(user),
                )
            }
            .attachExceptionHandling()
    }

    private fun Result<*>.attachExceptionHandling() = intent {
        onFailure { exception ->
            when {
                exception.isAppUpgradeRequire -> {
                    reduce { state.copy(isUpdateRequire = true) }
                }

                exception.isLoginRequireCode || exception.isUserNotFound -> {
                    postSideEffect(IntroSideEffect.UserNotInitialized)
                }

                exception.isTokenExpired -> {
                    postSideEffect(IntroSideEffect.GetMeError(exception))
                }

                else -> {
                    postSideEffect(IntroSideEffect.ReportError(exception))
                }
            }
        }
    }
}
