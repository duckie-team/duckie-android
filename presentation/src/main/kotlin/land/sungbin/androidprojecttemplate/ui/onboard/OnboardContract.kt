package land.sungbin.androidprojecttemplate.ui.onboard

import android.net.Uri
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import land.sungbin.androidprojecttemplate.data.domain.Category
import land.sungbin.androidprojecttemplate.data.domain.Tag
import java.net.URI

data class OnboardState(
    val onboardState: OnboardPage = OnboardPage.Profile,
    val profileModel: ProfileModel = ProfileModel(),
    val categoriesModel: CategoriesModel = CategoriesModel(),
    val tagModel: TagModel = TagModel(),
) {
    data class ProfileModel(
        val profileImage: URI = URI(""),
        val nickname: String = "",
    )

    data class CategoriesModel(
        val categories: PersistentList<Category> = emptyList<Category>().toPersistentList(),
        val selectedCategories: MutableList<Category> = mutableListOf(),
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