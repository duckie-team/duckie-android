/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.properties.Delegates
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.user.model.KakaoUser
import team.duckie.app.android.domain.user.usecase.KakaoLoginUseCase
import team.duckie.app.android.feature.ui.onboard.constaint.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.viewmodel.BaseViewModel

/**
 * 다음 단계를 진행하기 위한 최소한의 시간 간격 (단위: 초)
 */
private val NextStepNavigateThrottle = 1.seconds

class OnboardViewModel @AssistedInject constructor(
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
    @Assisted private val kakaoLoginUseCase: KakaoLoginUseCase,
) : BaseViewModel<OnboardState, OnboardSideEffect>(OnboardState.Initial) {
    private val nicknameFilter = Regex("[^가-힣a-zA-Z0-9_.]")
    private var lastestUpdateStepMillis = System.currentTimeMillis()

    /**
     * [selectedCategories] 의 mutable 한 객체를 나타냅니다.
     *
     * @see selectedCategories
     */
    private var mutableSelectedCategories = persistentListOf<String>()

    /**
     * [OnboardStep.Category] 에서 선택한 카테고리들을 나타냅니다.
     */
    val selectedCategories: ImmutableList<String> get() = mutableSelectedCategories

    /**
     * [KakaoLoginUseCase] 를 통해 얻어온 [KakaoUser] 객체를 나타냅니다.
     * `ProfileScreen` 에서 내 정보를 [KakaoUser] 값에 맞게 미리 채워넣기 위해 사용됩니다.
     */
    var me by Delegates.notNull<KakaoUser>()

    /**
     * [galleryImages] 의 mutable 한 객체를 나타냅니다.
     *
     * @see galleryImages
     */
    private var mutableGalleryImages = persistentListOf<String>()

    /**
     * [LoadGalleryImagesUseCase] 를 통해 얻어온 이미지 목록을 저장합니다.
     * `ProfileScreen` 에서 `PhotoPicker` 에 사용할 이미지 목록을 불러오기 위해 사용됩니다.
     */
    val galleryImages: ImmutableList<String> get() = mutableGalleryImages

    /**
     * 온보딩 단계를 업데이트합니다.
     * 다음 단계로 넘어가기 전에 발생하는 중복 업데이트를 방지하기 위해
     * 1000 ms 이내에는 업데이트가 되지 않습니다.
     *
     * @param step 새로운 온보딩 단계
     * @param ignoreThrottle 단계 업데이트 요청 Throttle 을 무시할지 여부
     */
    fun navigateStep(step: OnboardStep, ignoreThrottle: Boolean = false) {
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
                postSideEffect {
                    OnboardSideEffect.SaveUser(user)
                }
                updateState {
                    OnboardState.NavigateStep(nextStep)
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
                postSideEffect {
                    OnboardSideEffect.UpdateGalleryImages(images)
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
     *
     * @param category 태그를 추천받을 카테고리
     */
    // TODO: 서버 연동
    @Suppress("UNUSED_PARAMETER")
    fun getRecommendationTags(category: String): ImmutableList<String> {
        @AllowMagicNumber(because = "0..10")
        return persistentListOf(*((0..10).map { "태그 #$it" }).toTypedArray())
    }

    /**
     * `CategoryScreen` 에서 선택한 카테고리 목록을 저장합니다.
     */
    fun addSelectedCategories(categories: List<String>) {
        mutableSelectedCategories = mutableSelectedCategories.addAll(categories)
    }

    /**
     * `PhotoPicker` 에서 표시할 이미지 목록을 업데이트합니다.
     */
    fun addGalleryImages(images: List<String>) {
        mutableGalleryImages = mutableGalleryImages.addAll(images)
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(kakaoLoginUseCase: KakaoLoginUseCase): OnboardViewModel
    }
}
