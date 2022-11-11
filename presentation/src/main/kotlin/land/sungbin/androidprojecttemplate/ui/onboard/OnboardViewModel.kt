package land.sungbin.androidprojecttemplate.ui.onboard

import android.net.Uri
import kotlinx.collections.immutable.persistentListOf
import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.data.domain.Category
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardViewModel @Inject constructor() :
    BaseViewModel<OnboardState, OnboardSideEffect>(OnboardState()) {
    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        updateState {
            copy(
                categoriesModel = categoriesModel.copy(
                    categories = persistentListOf(
                        Category(0, "연예인"),
                        Category(1, "영화"),
                        Category(2, "만화/애니"),
                        Category(3, "웹툰"),
                        Category(4, "게임"),
                        Category(5, "밀리터리"),
                        Category(6, "IT"),
                        Category(7, "게임"),
                        Category(8, "밀리터리"),
                        Category(9, "IT"),
                    )
                )
            )
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


    fun onClickCategory(checked: Boolean, category: Category) {
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

    fun setProfileImage(image: URI) {
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