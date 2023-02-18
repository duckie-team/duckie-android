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
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.presentation.viewmodel.sideeffect.IntroSideEffect
import team.duckie.app.android.presentation.viewmodel.state.IntroState
import team.duckie.app.android.util.kotlin.ClientMeIdNull
import team.duckie.app.android.util.kotlin.ClientMeTokenNull
import team.duckie.app.android.util.kotlin.DuckieClientLogicProblemException

@HiltViewModel
internal class IntroViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMeUseCase: GetMeUseCase,
) : ContainerHost<IntroState, IntroSideEffect>, ViewModel() {

    override val container = container<IntroState, IntroSideEffect>(
        initialState = IntroState,
        savedStateHandle = savedStateHandle,
    )

    suspend fun getUser() = intent {
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
            if (exception is DuckieClientLogicProblemException &&
                (exception.code == ClientMeIdNull || exception.code == ClientMeTokenNull)
            ) {
                postSideEffect(IntroSideEffect.GetUserFinished(null))
            } else {
                postSideEffect(IntroSideEffect.ReportError(exception))
            }
        }
    }
}
