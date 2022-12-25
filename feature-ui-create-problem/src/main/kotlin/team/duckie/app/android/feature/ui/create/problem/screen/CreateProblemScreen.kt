/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.launch
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.icon.QuackIcon

private const val TopAppBarLayoutId = "CreateProblemScreenTopAppBarLayoutId"
private const val ContentLayoutId = "CreateProblemScreenContentLayoutId"
// private const val BottomLayoutId = "CreateProblemScreenBottomLayoutId"

private val createProblemMeasurePolicy = MeasurePolicy { measurableItems, constraints ->
    // 1. topAppBar, createProblemButton 높이값 측정
    val looseConstraints = constraints.asLoose()

    val topAppBarMeasurable = measurableItems.fastFirstOrNull { measureItem ->
        measureItem.layoutId == TopAppBarLayoutId
    }?.measure(looseConstraints) ?: npe()
    val topAppBarHeight = topAppBarMeasurable.height

    val contentThresholdHeight = constraints.maxHeight - topAppBarHeight
    val contentConstraints = constraints.copy(
        minHeight = contentThresholdHeight,
        maxHeight = contentThresholdHeight,
    )
    val contentMeasurable = measurableItems.fastFirstOrNull { measurable ->
        measurable.layoutId == ContentLayoutId
    }?.measure(contentConstraints) ?: npe()

    // 3. 위에서 추출한 값들을 활용해 레이아웃 위치 처리
    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarMeasurable.place(
            x = 0,
            y = 0,
        )
        contentMeasurable.place(
            x = 0,
            y = topAppBarHeight,
        )
    }
}

/** 문제 만들기 2단계 (문제 만들기) Screen */
@Composable
fun CreateProblemScreen(modifier: Modifier) = CoroutineScopeContent {
    val vm = LocalViewModel.current as CreateProblemViewModel
    val keyboard = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    BackHandler {
        vm.navigateStep(CreateProblemStep.ExamInformation)
    }

    LaunchedEffect(Unit) {
        val sheetStateFlow = snapshotFlow { sheetState.currentValue }
        sheetStateFlow.collect { state ->
            if (state == ModalBottomSheetValue.Hidden) {
                keyboard?.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetBackgroundColor = QuackColor.White.composeColor,
        scrimColor = QuackColor.Dimmed.composeColor,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
        ),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 상단 회색 마크
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(QuackColor.Gray2.composeColor)
                )

                // 선택 목록
                Column(modifier = Modifier.fillMaxWidth()) {
                    // 객관식/글 버튼
                    QuackSubtitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        padding = PaddingValues(
                            top = 12.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        ),
                        text = stringResource(id = R.string.create_problem_bottom_sheet_title_choice_text),
                        onClick = {
                            launch {
                                sheetState.hide()
                            }
                        }
                    )

                    // 객관식/사진 버튼
                    QuackSubtitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        padding = PaddingValues(
                            top = 12.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        ),
                        text = stringResource(id = R.string.create_problem_bottom_sheet_title_choice_media),
                        onClick = {
                            launch {
                                sheetState.hide()
                            }
                        }
                    )

                    // 주관식 버튼
                    QuackSubtitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        padding = PaddingValues(
                            top = 12.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        ),
                        text = stringResource(id = R.string.create_problem_bottom_sheet_title_short_form),
                        onClick = {
                            launch {
                                sheetState.hide()
                            }
                        }
                    )
                }
            }
        }
    ) {
        Layout(
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            measurePolicy = createProblemMeasurePolicy,
            content = {
                // 상단 탭바
                PrevAndNextTopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId(TopAppBarLayoutId),
                    onLeadingIconClick = {
                        launch { vm.navigateStep(CreateProblemStep.ExamInformation) }
                    },
                    trailingText = stringResource(id = R.string.next),
                    onTrailingTextClick = {
                        launch { vm.navigateStep(CreateProblemStep.AdditionalInformation) }
                    },
                    trailingTextEnabled = vm.isAllFieldsNotEmpty(),
                )

                // 컨텐츠 Layout
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .layoutId(ContentLayoutId),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = object : Arrangement.Vertical {
                        override fun Density.arrange(
                            totalSize: Int,
                            sizes: IntArray,
                            outPositions: IntArray
                        ) {
                            var current = 0
                            sizes.take(sizes.size - 1).forEachIndexed { index, it ->
                                outPositions[index] = current
                                current += it
                            }
                            outPositions[sizes.lastIndex] = totalSize - sizes.last()
                        }
                    },
                    content = {
                        items(19) {
                            QuackSubtitle(modifier = Modifier.height(20.dp), text = "abcdefg $it")
                        }

                        item {
                            QuackLargeButton(
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        bottom = 20.dp,
                                        top = 12.dp,
                                    ),
                                type = QuackLargeButtonType.Border,
                                text = stringResource(id = R.string.create_problem_add_problem_button),
                                leadingIcon = QuackIcon.Plus,
                            ) {
                                launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
                            }
                        }
                    }
                )
            }
        )
    }
}

/** 문제 만들기 2단계 최하단 Layout  */
@Deprecated("임시 저장 기능 부활 시 다시 사용")
@Composable
fun CreateProblemBottomLayout() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .layoutId(BottomLayoutId)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 20.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // 임시저장 버튼
        QuackLargeButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.create_problem_temp_save_button),
            type = QuackLargeButtonType.Border,
        ) {
        }

        // 다음 버튼
        QuackLargeButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.next),
            type = QuackLargeButtonType.Fill,
            enabled = false,
        ) {

        }
    }
}
