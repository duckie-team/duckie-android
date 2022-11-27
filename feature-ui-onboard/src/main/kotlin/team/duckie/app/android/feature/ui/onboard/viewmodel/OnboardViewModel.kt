/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.user.usecase.KakaoLoginUseCase
import team.duckie.app.android.feature.ui.onboard.viewmodel.constaint.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.viewmodel.BaseViewModel

/**
 * 다음 단계를 진행하기 위한 최소한의 시간 간격 (단위: 초)
 */
private val NextStepNavigateThrottle = 1.seconds

internal class OnboardViewModel(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
) : BaseViewModel<OnboardState, OnboardSideEffect>(OnboardState.Initial) {
    private val nicknameFilter = Regex("[^가-힣a-zA-Z0-9_.]")
    private var lastestUpdateStepMillis = System.currentTimeMillis()

    /**
     * [OnboardStep.Category] 에서 선택한 카테고리들을 나타냅니다.
     */
    val selectedCatagories = mutableListOf<String>()

    /**
     * 온보딩 단계를 업데이트합니다.
     * 다음 단계로 넘어가기 전에 발생하는 중복 업데이트를 방지하기 위해
     * 1000 ms 이내에는 업데이트가 되지 않습니다.
     *
     * @param step 새로운 온보딩 단계
     * @param ignoreThrottle 단계 업데이트 요청 Throttle 을 무시할지 여부
     */
    fun updateStep(step: OnboardStep, ignoreThrottle: Boolean = false) {
        if (!ignoreThrottle &&
            System.currentTimeMillis() - lastestUpdateStepMillis < NextStepNavigateThrottle
        ) {
            return
        }
        lastestUpdateStepMillis = System.currentTimeMillis()
        updateState {
            OnboardState.NavigateStep(step)
        }
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

    /**
     * 카카오 로그인을 요청합니다.
     *
     * 요청에 성공할시 [nextStep] 으로 온보딩 단계를 업데이트합니다.
     *
     * @param nextStep 카카오 로그인 다음에 진행할 온보딩 단계
     */
    suspend fun kakaoLogin(nextStep: OnboardStep) {
        kakaoLoginUseCase()
            .onSuccess { user ->
                updateState {
                    OnboardState.NavigateStep(nextStep)
                }
                postSideEffect {
                    OnboardSideEffect.SaveUser(user)
                }
            }
            .onFailure { expection ->
                updateState {
                    OnboardState.Error(expection)
                }
                postSideEffect {
                    OnboardSideEffect.ReportError(expection)
                }
            }
    }

    /**
     * 갤러리에서 이미지 목록을 조회합니다.
     */
    suspend fun loadGalleryImages() {
        loadGalleryImagesUseCase()
            .onSuccess { images ->
                updateState {
                    OnboardState.GalleryImageLoaded(images)
                }
            }
            .onFailure { expection ->
                updateState {
                    OnboardState.Error(expection)
                }
                postSideEffect {
                    OnboardSideEffect.ReportError(expection)
                }
            }
    }

    /**
     * 사용자 카테고리에 맞는 추천 태그들을 조회합니다.
     * 현재는 구현이 불가능하여 고정된 더미 값을 리턴합니다.
     */
    fun getRecommendationTags(): ImmutableList<String> {
        return persistentListOf("덕키", "이끔", "던던")
    }
}
