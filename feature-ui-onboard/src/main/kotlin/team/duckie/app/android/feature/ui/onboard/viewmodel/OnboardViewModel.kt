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
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.auth.usecase.CheckAccessTokenUseCase
import team.duckie.app.android.domain.auth.usecase.JoinUseCase
import team.duckie.app.android.domain.category.usecase.GetCategoriesUseCase
import team.duckie.app.android.domain.file.usecase.FileUploadUseCase
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.kakao.usecase.GetKakaoAccessTokenUseCase
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.impl.ApiViewModelInstance
import team.duckie.app.android.feature.ui.onboard.viewmodel.impl.PermissionViewModelInstance
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.viewmodel.BaseViewModel

private val NextStepNavigateThrottle = 1.seconds

internal class OnboardViewModel @AssistedInject constructor(
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
    private val joinUseCase: JoinUseCase,
    private val checkAccessTokenUseCase: CheckAccessTokenUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val fileUploadUseCase: FileUploadUseCase,
    private val tagCreateUseCase: TagCreateUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
    @Assisted private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
) : BaseViewModel<OnboardState, OnboardSideEffect>(OnboardState.Initial),
    PermissionViewModel by PermissionViewModelInstance,
    ApiViewModel by ApiViewModelInstance(
        getKakaoAccessTokenUseCase = getKakaoAccessTokenUseCase,
        joinUseCase = joinUseCase,
        checkAccessTokenUseCase = checkAccessTokenUseCase,
        getCategoriesUseCase = getCategoriesUseCase,
        fileUploadUseCase = fileUploadUseCase,
        tagCreateUseCase = tagCreateUseCase,
        userUpdateUseCase = userUpdateUseCase,
    ) {

    init {
        setEventHandler { event ->
            updateState { event }
        }

        setSideEffectHandler { effect ->
            postSideEffect { effect }
        }

        setExceptionHandler { exception ->
            updateState {
                OnboardState.Error(exception)
            }
            postSideEffect {
                OnboardSideEffect.ReportError(exception)
            }
        }
    }

    private val nicknameFilter = Regex("[^가-힣a-zA-Z0-9_.]")
    private var lastestUpdateStepMillis = System.currentTimeMillis()

    var selectedCategories: ImmutableList<String> = persistentListOf()

    var me by Delegates.notNull<User>()

    private var mutableGalleryImages = persistentListOf<String>()

    val galleryImages: ImmutableList<String> get() = mutableGalleryImages

    fun navigateStep(step: OnboardStep, ignoreThrottle: Boolean = false) {
        if (!ignoreThrottle &&
            System.currentTimeMillis() - lastestUpdateStepMillis < NextStepNavigateThrottle
        ) return
        lastestUpdateStepMillis = System.currentTimeMillis()
        updateState {
            OnboardState.NavigateStep(step)
        }
    }

    fun checkNicknameRuleError(nickname: String): Boolean {
        return nicknameFilter.containsMatchIn(nickname)
    }

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

    fun setSelectedCategories(categories: List<String>) {
        selectedCategories = categories.toImmutableList()
    }

    fun addGalleryImages(images: List<String>) {
        mutableGalleryImages = mutableGalleryImages.addAll(images)
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase): OnboardViewModel
    }
}
