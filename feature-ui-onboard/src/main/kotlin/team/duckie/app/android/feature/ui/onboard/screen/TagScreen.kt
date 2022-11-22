@file:OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class,
)

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.combine
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.launch
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastAny
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasic2TextField
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackLazyVerticalGridTag
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun TagScreen() = CoroutineScopeContent {
    val vm = LocalViewModel.current as OnboardViewModel
    val category = vm.selectedCatagory
    var isStartable by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val addedTags = remember { mutableStateListOf<String>() }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetBackgroundColor = QuackColor.White.composeColor,
        // TODO: QuackColor.Dimmed 추가
        scrimColor = QuackColor.Black.change(alpha = 0.6f).composeColor,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp, topEnd = 16.dp
        ),
        sheetContent = {
            TagScreenModalBottomSheetContent(
                onDismissRequest = { newAddedTags ->
                    launch {
                        sheetState.hide()
                        addedTags.addAll(newAddedTags)
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = systemBarPaddings.calculateBottomPadding())
                .padding(bottom = 18.dp),
        ) {
            OnboardTopAppBar(showSkipTrailingText = true)
            TagSelection(
                modifier = Modifier.padding(
                    top = 12.dp,
                    start = 20.dp,
                    end = 20.dp,
                ),
                category = category,
                sheetState = sheetState,
                addedTags = addedTags,
                startableUpdate = { startable ->
                    isStartable = startable
                },
            )
            QuackLargeButton(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(id = R.string.button_start_duckie),
                type = QuackLargeButtonType.Fill,
                enabled = isStartable,
            ) {
                // TODO: 온보딩 완료
            }
        }
    }
}

@Composable
private fun TagSelection(
    modifier: Modifier,
    category: String,
    sheetState: ModalBottomSheetState,
    addedTags: SnapshotStateList<String>,
    startableUpdate: (startable: Boolean) -> Unit,
) = CoroutineScopeContent {
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

    LaunchedEffect(Unit) {
        val hottestTagSelectionsFlow = snapshotFlow { hottestTagSelections }
        val addedTagsFlow = snapshotFlow { addedTags }

        // hottest tag 에 최소 1개가 선택됐거나, 사용자가 최소 1개의 태그를 추가했을 때
        hottestTagSelectionsFlow.combine(addedTagsFlow) { hottestTagSelections, addedTags ->
            hottestTagSelections.fastAny { it } || addedTags.isNotEmpty()
        }.collect { startable ->
            startableUpdate(startable)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
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
                modifier = Modifier.fillMaxWidth(),
                visible = addedTags.isNotEmpty(),
                otherEnterAnimation = scaleIn(animationSpec = QuackAnimationSpec()),
                otherExitAnimation = scaleOut(animationSpec = QuackAnimationSpec()),
            ) {
                QuackLazyVerticalGridTag(
                    modifier = Modifier.fillMaxWidth(),
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
                    launch {
                        sheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                },
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            QuackTitle2(text = stringResource(R.string.tag_hottest_tag, category))
            // TODO: chunkedItems 기준 인자로 추가
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

@Composable
private fun TagScreenModalBottomSheetContent(
    onDismissRequest: (addedTags: List<String>) -> Unit,
) {
    val inputtedTags = remember { mutableStateListOf<String>() }
    var tagInput by remember { mutableStateOf("") }

    fun updateTagInput() {
        if (tagInput.isNotBlank()) {
            inputtedTags.add(tagInput)
            tagInput = ""
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackTitle2(text = stringResource(R.string.tag_added_tag))
            // TODO: 터치 영역 조정
            QuackSubtitle(
                text = stringResource(R.string.button_done),
                color = QuackColor.DuckieOrange,
                onClick = { onDismissRequest(inputtedTags) },
            )
        }
        QuackAnimatedVisibility(
            modifier = Modifier.padding(top = 10.dp),
            visible = inputtedTags.isNotEmpty(),
            otherEnterAnimation = scaleIn(animationSpec = QuackAnimationSpec()),
            otherExitAnimation = scaleOut(animationSpec = QuackAnimationSpec()),
        ) {
            QuackLazyVerticalGridTag(
                items = inputtedTags,
                tagType = QuackTagType.Circle(trailingIcon = QuackIcon.Close),
                onClick = { index ->
                    inputtedTags.remove(inputtedTags[index])
                },
            )
        }
        QuackBasic2TextField(
            modifier = Modifier.padding(top = 16.dp),
            text = tagInput,
            onTextChanged = { tagInput = it },
            placeholderText = stringResource(R.string.tag_add_manual_placeholder),
            trailingIcon = QuackIcon.ArrowSend,
            trailingIconOnClick = ::updateTagInput,
            keyboardActions = KeyboardActions {
                updateTagInput()
            },
        )
    }
}
