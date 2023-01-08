/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel

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
    private val checkAccessTokenUseCase: CheckAccessTokenUseCase,
    private val attachAccessTokenToHeaderUseCase: AttachAccessTokenToHeaderUseCase,
) : ContainerHost<PresentationState, PresentationSideEffect>, ViewModel() {
    override val container = container<PresentationState, PresentationSideEffect>(PresentationState.Initial)

    fun checkAccessToken(accessToken: String) = intent {
        checkAccessTokenUseCase(accessToken)
            .onSuccess { pass ->
                if (pass) {
                    postSideEffect(PresentationSideEffect.AttachAccessTokenToHeader(accessToken))
                } else {
                    reduce {
                        PresentationState.AccessTokenValidationFailed
                    }
                }
            }
            .attachExceptionHandling()
    }

    fun attachAccessTokenToHeader(accessToken: String) = intent {
        attachAccessTokenToHeaderUseCase(accessToken)
            .onSuccess {
                reduce {
                    PresentationState.AttachedAccessTokenToHeader
                }
            }
            .attachExceptionHandling()
    }

    private fun Result<*>.attachExceptionHandling() = intent {
        onFailure { exception ->
            reduce {
                PresentationState.Error(exception)
            }
            postSideEffect(PresentationSideEffect.ReportError(exception))
        }
    }
}
