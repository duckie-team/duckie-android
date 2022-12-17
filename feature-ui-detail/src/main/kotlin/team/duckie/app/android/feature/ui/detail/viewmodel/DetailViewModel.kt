/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail.viewmodel

import javax.inject.Inject
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
import team.duckie.app.android.util.viewmodel.BaseViewModel

class DetailViewModel @Inject constructor() : BaseViewModel<DetailState, DetailSideEffect>(DetailState.Initial) {
    suspend fun sendToast(message: String) {
        postSideEffect {
            DetailSideEffect.SendToast(message)
        }
    }
}
