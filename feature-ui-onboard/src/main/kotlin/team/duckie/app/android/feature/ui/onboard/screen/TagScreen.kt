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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
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
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.launch
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastAny
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
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

private const val TagScreenTopAppBarLayoutId = "TagScreenTopAppBar"
private const val TagScreenTagSelectionLayoutId = "TagScreenTagSelection"
private const val TagScreenQuackLargeButtonLayoutId = "TagScreenQuackLargeButton"

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
            topStart = 16.dp,
            topEnd = 16.dp,
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
        Layout(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = systemBarPaddings.calculateBottomPadding())
                .padding(bottom = 16.dp),
            content = {
                OnboardTopAppBar(
                    modifier = Modifier.layoutId(TagScreenTopAppBarLayoutId),
                    showSkipTrailingText = true,
                )
                TagSelection(
                    modifier = Modifier
                        .layoutId(TagScreenTagSelectionLayoutId)
                        .padding(
                            top = 12.dp,
                            start = 20.dp,
                            end = 20.dp,
                        ),
                    category = category,
                    sheetState = sheetState,
                    addedTags = addedTags.toList(),
                    requestRemoveAddedTag = { index ->
                        addedTags.remove(addedTags[index])
                    },
                    startableUpdate = { startable ->
                        isStartable = startable
                    },
                )
                QuackLargeButton(
                    modifier = Modifier
                        .layoutId(TagScreenQuackLargeButtonLayoutId)
                        .padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.button_start_duckie),
                    type = QuackLargeButtonType.Fill,
                    enabled = isStartable,
                ) {
                    // TODO: 온보딩 완료
                }
            }
        ) { measurables, constraints ->
            val looseConstraints = constraints.asLoose()

            val topAppBarPlaceable = measurables.fastFirstOrNull { measurable ->
                measurable.layoutId == TagScreenTopAppBarLayoutId
            }?.measure(looseConstraints) ?: npe()

            val tagSelectionPlaceable = measurables.fastFirstOrNull { measurable ->
                measurable.layoutId == TagScreenTagSelectionLayoutId
            }?.measure(looseConstraints) ?: npe()

            val quackLargeButtonPlaceable = measurables.fastFirstOrNull { measurable ->
                measurable.layoutId == TagScreenQuackLargeButtonLayoutId
            }?.measure(looseConstraints) ?: npe()

            layout(
                width = constraints.maxWidth,
                height = constraints.maxHeight,
            ) {
                topAppBarPlaceable.place(
                    x = 0,
                    y = 0,
                )
                tagSelectionPlaceable.place(
                    x = 0,
                    y = topAppBarPlaceable.height,
                )
                quackLargeButtonPlaceable.place(
                    x = 0,
                    y = constraints.maxHeight - quackLargeButtonPlaceable.height,
                )
            }
        }
    }
}

@Composable
private fun TagSelection(
    modifier: Modifier,
    category: String,
    sheetState: ModalBottomSheetState,
    addedTags: List<String>,
    requestRemoveAddedTag: (index: Int) -> Unit,
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

    LaunchedEffect(hottestTagSelections, addedTags) {
        // https://stackoverflow.com/a/70429284/14299073
        val hottestTagSelectionsFlow = snapshotFlow { hottestTagSelections.toList() }
        val addedTagsFlow = snapshotFlow { addedTags.toList() }

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
                        requestRemoveAddedTag(index)
                    },
                )
            }
            QuackHeadLine2(
                text = stringResource(R.string.tag_add_manual),
                color = QuackColor.DuckieOrange,
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = systemBarPaddings.calculateBottomPadding()),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackTitle2(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.tag_added_tag),
            )
            QuackSubtitle(
                modifier = Modifier.padding(
                    vertical = 18.dp,
                    horizontal = 16.dp,
                ),
                text = stringResource(R.string.button_done),
                color = QuackColor.DuckieOrange,
                onClick = {
                    onDismissRequest(inputtedTags)
                    inputtedTags.clear()
                },
            )
        }
        QuackAnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            visible = inputtedTags.isNotEmpty(),
        ) {
            // TODO: chunkedItems 기준 인자로 추가
            // TODO: 컨텐츠 사이 간격 인자로 추가
            // TODO: contentPadding 추가
            QuackLazyVerticalGridTag(
                modifier = Modifier.fillMaxWidth(),
                items = inputtedTags,
                tagType = QuackTagType.Circle(trailingIcon = QuackIcon.Close),
                onClick = { index ->
                    inputtedTags.remove(inputtedTags[index])
                },
            )
        }
        // TODO: underline 엔 영향을 미치지 않는 padding 추가
        QuackBasic2TextField(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
            ),
            text = tagInput,
            onTextChanged = { tagInput = it },
            placeholderText = stringResource(R.string.tag_add_manual_placeholder),
            trailingIcon = QuackIcon.ArrowSend,
            trailingIconOnClick = ::updateTagInput,
            keyboardActions = KeyboardActions { updateTagInput() },
        )
    }
}
