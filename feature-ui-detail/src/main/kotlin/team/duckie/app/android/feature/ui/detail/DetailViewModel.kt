/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail

import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
import team.duckie.app.android.util.viewmodel.BaseViewModel

internal class DetailViewModel : BaseViewModel<DetailState, DetailSideEffect>(DetailState.Initial) {
    suspend fun sendToast(message: String) {
        postSideEffect {
            DetailSideEffect.SendToast(message)
        }
    }
}

/** 상세 화면에서 사용되는 클릭 이벤트 모음 */
sealed class DetailClickEvent
