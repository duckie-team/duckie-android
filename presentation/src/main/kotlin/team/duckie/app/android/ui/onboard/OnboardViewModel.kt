package team.duckie.app.android.ui.onboard

import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.base.BaseViewModel
import team.duckie.app.domain.model.constraint.LikeCategory
import team.duckie.app.domain.usecase.user.FetchCategoriesUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardViewModel @Inject constructor(
    private val fetchCategoriesUseCase: FetchCategoriesUseCase,
) :
    BaseViewModel<OnboardState, OnboardSideEffect>(OnboardState()) {

    suspend fun fetchCategories() {
        fetchCategoriesUseCase().onSuccess { categories: List<LikeCategory> ->
            updateState {
                copy(
                    categoriesModel = categoriesModel.copy(
                        categories = categories.toPersistentList()
                    )
                )
            }
        }.onFailure {
            updateState {
                copy(
                    categoriesModel = categoriesModel.copy(
                        categories = emptyList<LikeCategory>().toPersistentList()
                    )
                )
            }
        }
    }

    fun navigatePage(page: OnboardPage) = updateState {
        copy(onboardState = page)
    }

    fun setNickName(nickname: String) = updateState {
        copy(
            profileModel = profileModel.copy(
                nickname = nickname
            )
        )
    }

    suspend fun onClickComplete() {
        postSideEffect { OnboardSideEffect.NavigateToMain }
    }

    suspend fun onClickProfile() {
        postSideEffect { OnboardSideEffect.NavigateToGalley }
    }


    fun onClickCategory(
        checked: Boolean,
        category: LikeCategory,
    ) {
        updateState {
            when (checked) {
                true -> {
                    copy(
                        categoriesModel = categoriesModel.copy(
                            selectedCategories = (categoriesModel.selectedCategories + category).toMutableList()
                        )
                    )
                }

                else -> {
                    copy(
                        categoriesModel = categoriesModel.copy(
                            selectedCategories = (categoriesModel.selectedCategories - category).toMutableList()
                        )
                    )
                }
            }
        }
    }

    fun setProfileImage(image: String) {
        updateState {
            copy(
                profileModel = profileModel.copy(
                    profileImage = image
                )
            )
        }
    }

    suspend fun handleBackPressed() {
        when (currentState.onboardState) {
            OnboardPage.Profile -> {
                postSideEffect { OnboardSideEffect.NavigateToLogin }
            }

            OnboardPage.Categories -> {
                navigatePage(OnboardPage.Profile)
            }

            OnboardPage.Tag -> {
                navigatePage(OnboardPage.Categories)
            }
        }
    }
}
