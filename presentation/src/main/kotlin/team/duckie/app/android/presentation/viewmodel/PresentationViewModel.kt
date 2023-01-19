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
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.auth.usecase.AttachAccessTokenToHeaderUseCase
import team.duckie.app.android.domain.auth.usecase.CheckAccessTokenUseCase
import team.duckie.app.android.presentation.viewmodel.sideeffect.PresentationSideEffect
import team.duckie.app.android.presentation.viewmodel.state.PresentationState

@HiltViewModel
internal class PresentationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val checkAccessTokenUseCase: CheckAccessTokenUseCase,
    private val attachAccessTokenToHeaderUseCase: AttachAccessTokenToHeaderUseCase,
) : ContainerHost<PresentationState, PresentationSideEffect>, ViewModel() {

    override val container = container<PresentationState, PresentationSideEffect>(
        initialState = PresentationState(),
        savedStateHandle = savedStateHandle,
    )

    suspend fun attachAccessTokenToHeaderIfValidationPassed(accessToken: String) = intent {
        checkAccessTokenUseCase(accessToken)
            .onSuccess { pass ->
                if (pass) {
                    postSideEffect(PresentationSideEffect.AttachAccessTokenToHeader(accessToken))
                } else {
                    reduce {
                        state.copy(accessTokenValidationFail = true)
                    }
                }
            }
            .attachExceptionHandling()
    }

    fun attachAccessTokenToHeader(accessToken: String) = intent {
        attachAccessTokenToHeaderUseCase(accessToken)
            .onSuccess {
                reduce {
                    state.copy(accessTokenAttachedToHeader = true)
                }
            }
            .attachExceptionHandling()
    }

    private fun Result<*>.attachExceptionHandling() = intent {
        onFailure { exception ->
            postSideEffect(PresentationSideEffect.ReportError(exception))
        }
    }
}
