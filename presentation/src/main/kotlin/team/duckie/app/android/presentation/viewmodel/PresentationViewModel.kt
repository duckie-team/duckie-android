/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel

import javax.inject.Inject
import team.duckie.app.android.domain.auth.usecase.AttachAccessTokenToHeaderUseCase
import team.duckie.app.android.domain.auth.usecase.CheckAccessTokenUseCase
import team.duckie.app.android.presentation.viewmodel.sideeffect.PresentationSideEffect
import team.duckie.app.android.presentation.viewmodel.state.PresentationState
import team.duckie.app.android.util.viewmodel.BaseViewModel

internal class PresentationViewModel @Inject constructor(
    private val checkAccessTokenUseCase: CheckAccessTokenUseCase,
    private val attachAccessTokenToHeaderUseCase: AttachAccessTokenToHeaderUseCase,
) : BaseViewModel<PresentationState, PresentationSideEffect>(PresentationState.Initial) {
    suspend fun checkAccessToken(accessToken: String) {
        checkAccessTokenUseCase(accessToken)
            .onSuccess { pass ->
                if (pass) {
                    postSideEffect {
                        PresentationSideEffect.AttachAccessTokenToHeader(accessToken)
                    }
                } else {
                    updateState {
                        PresentationState.AccessTokenValidationFailed
                    }
                }
            }
            .attachExceptionHandling()
    }

    suspend fun attachAccessTokenToHeader(accessToken: String) {
        attachAccessTokenToHeaderUseCase(accessToken)
            .onSuccess {
                updateState {
                    PresentationState.AttachedAccessTokenToHeader
                }
            }
            .attachExceptionHandling()
    }

    private suspend fun Result<*>.attachExceptionHandling() {
        onFailure { exception ->
            updateState {
                PresentationState.Error(exception)
            }
            postSideEffect {
                PresentationSideEffect.ReportError(exception)
            }
        }
    }
}
