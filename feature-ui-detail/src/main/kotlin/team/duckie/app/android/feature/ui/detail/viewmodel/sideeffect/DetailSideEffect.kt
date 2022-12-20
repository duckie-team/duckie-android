/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect

import team.duckie.app.android.feature.ui.detail.viewmodel.DetailViewModel
import team.duckie.app.android.feature.ui.detail.viewmodel.state.ClickEvent

/** [DetailViewModel] 에서 사용되는 SideEffect 모음 */
sealed class DetailSideEffect {
    class SendToast(val message: String) : DetailSideEffect()
    class Click(val event: ClickEvent) : DetailSideEffect()
}
