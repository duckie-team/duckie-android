package land.sungbin.androidprojecttemplate.ui.onboard

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.ui.component.FadeAnimatedVisibility
import land.sungbin.androidprojecttemplate.ui.component.whiteGradient
import team.duckie.quackquack.ui.component.QuackLargeButton


@Composable
fun OnboardScreen(
    viewModel: OnboardViewModel,
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Crossfade(
        targetState = state.onboardState
    ) { pageState ->
        when (pageState) {
            OnboardPage.Profile -> OnboardProfileScreen(
                nickname = state.profileModel.nickname,
                profileImage = state.profileModel.profileImage,
                onClickBack = {
                    coroutineScope.launch {
                        viewModel.handleBackPressed()
                    }
                },
                onNickNameChange = { nickname ->
                    viewModel.setNickName(nickname)
                },
                onClickNext = {
                    viewModel.navigatePage(OnboardPage.Categories)
                },
                onClickProfile = {
                    coroutineScope.launch {
                        viewModel.handleSideEffect(OnboardSideEffect.NavigateToGalley)
                    }
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
                    coroutineScope.launch {
                        viewModel.handleBackPressed()
                    }
                },
                onClickNext = {
                    viewModel.navigatePage(OnboardPage.Tag)
                }
            )

            OnboardPage.Tag -> OnboardTagScreen(
                categories = state.categoriesModel.selectedCategories.toPersistentList(),
                onClickBack = {
                    coroutineScope.launch {
                        viewModel.handleBackPressed()
                    }
                },
                onClickComplete = {
                    coroutineScope.launch {
                        viewModel.handleSideEffect(OnboardSideEffect.NavigateToMain)
                    }
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