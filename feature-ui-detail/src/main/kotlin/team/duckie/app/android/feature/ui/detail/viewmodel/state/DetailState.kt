/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.detail.viewmodel.state
import team.duckie.app.android.feature.ui.detail.DetailViewModel

/**
 * [DetailViewModel] 의 상태를 나타냅니다.
 */
internal sealed class DetailState {
    /** [DetailViewModel] 의 초기 상태를 나타냅니다. */
    object Initial : DetailState()

    /**
     * [DetailViewModel] 의 비즈니스 로직 처리중에 예외가 발생한 상태를 나타냅니다.
     *
     * @param exception 발생한 예외
     */
    class Error(val exception: Throwable) : DetailState()
}
