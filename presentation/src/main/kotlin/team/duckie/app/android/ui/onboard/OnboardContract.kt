package team.duckie.app.android.ui.onboard

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.domain.model.constraint.LikeCategory
import team.duckie.app.domain.model.constraint.Tag

data class OnboardState(
    val onboardState: OnboardPage = OnboardPage.Profile,
    val profileModel: ProfileModel = ProfileModel(),
    val categoriesModel: CategoriesModel = CategoriesModel(),
    val tagModel: TagModel = TagModel(),
) {
    data class ProfileModel(
        val profileImage: String = "",
        val nickname: String = "",
    )

    data class CategoriesModel(
        val categories: PersistentList<LikeCategory> = emptyList<LikeCategory>().toPersistentList(),
        val selectedCategories: MutableList<LikeCategory> = mutableListOf(),
    )

    data class TagModel(
        val selectedTags: List<Tag> = emptyList(),
    )
}

enum class OnboardPage {
    Profile,
    Categories,
    Tag
}

sealed class OnboardSideEffect {
    object NavigateToLogin : OnboardSideEffect()
    object NavigateToMain : OnboardSideEffect()
    object NavigateToGalley : OnboardSideEffect()
}
