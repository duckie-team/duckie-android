package land.sungbin.androidprojecttemplate.ui.onboard

import android.app.Activity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant
import land.sungbin.androidprojecttemplate.ui.component.FadeAnimatedVisibility
import land.sungbin.androidprojecttemplate.ui.component.whiteGradient
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import team.duckie.quackquack.ui.component.QuackLargeButton

private const val ONBOARD_PROFILE_PAGE = 0
private const val ONBOARD_CATEGORY_PAGE = 1
private const val ONBOARD_TAG_PAGE = 2

@Composable
fun OnboardScreen(
    activity: Activity,
    navigator: DuckieNavigator,
    viewModel: OnboardViewModel,
) {
    Crossfade(
        targetState = viewModel.currentPage.observeAsState().value ?: ONBOARD_PROFILE_PAGE
    ) { page ->

        when (page) {
            ONBOARD_PROFILE_PAGE -> OnboardProfileScreen(
                nickname = viewModel.nickname.observeAsState().value.orEmpty(),
                profileImage = viewModel.profileImage.observeAsState().value,
                onNickNameChange = { nickname ->
                    viewModel.setNickName(nickname)
                },
                onClickBack = {
                    activity.finish()
                },
                onClickNext = {
                    viewModel.navigatePage(ONBOARD_CATEGORY_PAGE)
                },
                onClickProfile = {
                    navigator.navigateGalleryScreen(
                        activity = activity,
                        imageSelectType = ApplicationConstant.IMAGE_SINGLE_TYPE,
                    )
                },
            )

            ONBOARD_CATEGORY_PAGE -> OnboardCategoryScreen(
                isNextButtonVisible = viewModel.selectedCategories.observeAsState().value?.isNotEmpty()
                    ?: false,
                categories = viewModel.categories.observeAsState().value ?: persistentListOf(),
                selectedCategories = viewModel.selectedCategories.observeAsState().value?.toPersistentList()
                    ?: persistentListOf(),
                onClickCategory = { checked, category ->
                    viewModel.onClickCategory(checked, category)
                },
                onClickBack = {
                    viewModel.navigatePage(ONBOARD_PROFILE_PAGE)
                },
                onClickNext = {
                    viewModel.navigatePage(ONBOARD_TAG_PAGE)
                }
            )

            ONBOARD_TAG_PAGE -> OnboardTagScreen(
                categories = viewModel.selectedCategories.observeAsState().value?.toPersistentList()
                    ?: persistentListOf(),
                onClickBack = {
                    viewModel.navigatePage(ONBOARD_CATEGORY_PAGE)
                },
                onClickComplete = {
                    viewModel.complete()
                }
            )
        }
    }
}

@Composable
fun OnboardingButton(
    title: String,
    isVisible: Boolean = true,
    active: Boolean = true,
    onClick: () -> Unit,
) {
    FadeAnimatedVisibility(
        visible = isVisible,
    ) {
        Box(
            modifier = Modifier
                .whiteGradient()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter,

            ) {
            QuackLargeButton(
                text = title,
                onClick = onClick,
                active = active,
            )
        }
    }
}