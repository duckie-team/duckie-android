package team.duckie.app.android.feature.ui.onboard.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.component.QuackTitle2

@Composable
internal fun TagScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 12.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 18.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

    }
}

@Composable
private fun TagSelection() {
    // TODO: 인기 태그 조회
    val vm = LocalViewModel.current as OnboardViewModel
    val selectedTags = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(28.dp),
    ) {
        TitleAndDescription(
            titleRes = R.string.tag_title,
            descriptionRes = R.string.tag_description,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            QuackTitle2(text = stringResource(R.string.tag_added_tag))
            // TODO: QuackAnimatedVisibility
            AnimatedVisibility(
                visible = selectedTags.isNotEmpty(),
                enter = fadeIn(animationSpec = QuackAnimationSpec()),
                exit = fadeOut(animationSpec = QuackAnimationSpec()),
            ) {

            }
        }
    }
}