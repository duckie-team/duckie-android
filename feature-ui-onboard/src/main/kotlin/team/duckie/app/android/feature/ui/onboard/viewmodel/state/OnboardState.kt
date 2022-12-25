/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.state

import team.duckie.app.android.feature.ui.onboard.OnboardActivity
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel

/**
 * [OnboardViewModel] 의 상태를 나타냅니다.
 */
internal sealed class OnboardState {
    /**
     * [OnboardViewModel] 의 초기 상태를 나타냅니다. 이 상태를 받으면
     * [OnboardStep.Login] 으로 [NavigateStep] 를 진행합니다.
     */
    object Initial : OnboardState()

    /**
     * [OnboardActivity] 의 현재 네비게이션 단계를 조정합니다.
     *
     * @param step 조정할 [OnboardStep] 정보
     */
    class NavigateStep(val step: OnboardStep) : OnboardState()

    /**
     * [OnboardViewModel] 의 비즈니스 로직 처리중에 예외가 발생한 상태를 나타냅니다.
     *
     * @param exception 발생한 예외
     */
    class Error(val exception: Throwable) : OnboardState()
}
