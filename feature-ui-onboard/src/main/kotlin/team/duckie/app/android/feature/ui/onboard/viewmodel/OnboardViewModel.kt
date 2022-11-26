/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import team.duckie.app.android.util.viewmodel.BaseViewModel

internal class OnboardViewModel : BaseViewModel<Unit, Unit>(Unit) {
    private val nicknameFilter = Regex("[^가-힣a-zA-Z0-9_.]")
    private val mutableStep = MutableStateFlow(OnboardStep.Login)
    private var lastestUpdateStepMillis = System.currentTimeMillis()

    /**
     * 현재 온보딩이 어느 단계에 있는지 나타냅니다.
     */
    val step = mutableStep.asStateFlow()
    val currentStep get() = step.value

    /**
     * [OnboardStep.Category] 에서 선택한 카테고리를 나타냅니다.
     */
    lateinit var selectedCatagory: String

    /**
     * 온보딩 단계를 업데이트합니다.
     * 다음 단계로 넘어가기 전에 발생하는 중복 업데이트를 방지하기 위해
     * 1000 ms 이내에는 업데이트가 되지 않습니다.
     *
     * @param step 새로운 온보딩 단계
     */
    fun updateStep(step: OnboardStep) {
        if (System.currentTimeMillis() - lastestUpdateStepMillis < 1000) {
            return
        }
        lastestUpdateStepMillis = System.currentTimeMillis()
        mutableStep.value = step
    }

    /**
     * 주어진 닉네임이 덕키 닉네임 규칙에 어긋나는지 검사합니다.
     *
     * @param nickname 검사할 닉네임
     *
     * @return 닉네임이 규칙에 어긋나는지 여부
     */
    fun checkNicknameRuleError(nickname: String): Boolean {
        return nicknameFilter.containsMatchIn(nickname)
    }
}
