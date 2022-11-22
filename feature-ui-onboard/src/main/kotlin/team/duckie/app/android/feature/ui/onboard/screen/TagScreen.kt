@file:OptIn(ExperimentalAnimationApi::class)

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackLazyVerticalGridTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon

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
private fun TagSelection(category: String) {
    val vm = LocalViewModel.current as OnboardViewModel
    val hottestTags = remember {
        // TODO: 인기 태그 조회
        persistentListOf("덕키", "이끔", "던던")
    }
    val hottestTagSelections = remember(hottestTags.size) {
        mutableStateListOf(
            elements = Array(
                size = hottestTags.size,
                init = { false },
            )
        )
    }
    val addedTags = remember { mutableStateListOf<String>() }

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
            QuackAnimatedVisibility(
                visible = addedTags.isNotEmpty(),
                otherEnterAnimation = scaleIn(animationSpec = QuackAnimationSpec()),
                otherExitAnimation = scaleOut(animationSpec = QuackAnimationSpec()),
            ) {
                QuackLazyVerticalGridTag(
                    items = addedTags,
                    tagType = QuackTagType.Circle(trailingIcon = QuackIcon.Close),
                    onClick = { index ->
                        addedTags.remove(addedTags[index])
                    },
                )
            }
            QuackHeadLine2(
                text = stringResource(R.string.tag_add_manual),
                onClick = {
                    // TODO: BottomSheet
                },
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            QuackTitle2(text = stringResource(R.string.tag_hottest_tag, category))
            QuackLazyVerticalGridTag(
                items = hottestTags,
                itemSelections = hottestTagSelections,
                tagType = QuackTagType.Round,
                onClick = { index ->
                    hottestTagSelections[index] = !hottestTagSelections[index]
                },
            )
        }
    }
}