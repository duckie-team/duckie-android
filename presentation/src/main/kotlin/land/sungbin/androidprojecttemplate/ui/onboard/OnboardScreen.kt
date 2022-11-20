package land.sungbin.androidprojecttemplate.ui.onboard

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toPersistentList
import land.sungbin.androidprojecttemplate.shared.compose.extension.CoroutineScopeContent
import land.sungbin.androidprojecttemplate.shared.compose.extension.launch
import land.sungbin.androidprojecttemplate.ui.component.FadeAnimatedVisibility
import land.sungbin.androidprojecttemplate.ui.component.whiteGradient
import team.duckie.quackquack.ui.component.QuackLargeButton

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun OnboardScreen(
    viewModel: OnboardViewModel,
) = CoroutineScopeContent {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Crossfade(
        targetState = state.onboardState
    ) { pageState ->
        when (pageState) {
            OnboardPage.Profile -> OnboardProfileScreen(
                nickname = state.profileModel.nickname,
                profileImage = state.profileModel.profileImage,
                onClickBack = {
                    launch { viewModel.handleBackPressed() }
                },
                onNickNameChange = { nickname ->
                    viewModel.setNickName(nickname)
                },
                onClickNext = {
                    viewModel.navigatePage(OnboardPage.Categories)
                },
                onClickProfile = {
                    launch { viewModel.onClickProfile() }
                },
            )

            OnboardPage.Categories -> OnboardCategoryScreen(
                isNextButtonVisible = state.categoriesModel.categories.isNotEmpty(),
                categories = state.categoriesModel.categories,
                selectedCategories = state.categoriesModel.selectedCategories.toPersistentList(),
                onClickCategory = { checked, category ->
                    viewModel.onClickCategory(checked, category)
                },
                onClickBack = {
                    launch { viewModel.handleBackPressed() }
                },
                onClickNext = {
                    viewModel.navigatePage(OnboardPage.Tag)
                }
            )

            OnboardPage.Tag -> OnboardTagScreen(
                categories = state.categoriesModel.selectedCategories.toPersistentList(),
                onClickBack = {
                    launch { viewModel.handleBackPressed() }
                },
                onClickComplete = {
                    launch { viewModel.onClickComplete() }
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