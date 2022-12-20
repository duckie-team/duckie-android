/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.ImeActionNext
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.common.TitleAndComponent
import team.duckie.app.android.feature.ui.create.problem.common.moveDownFocus
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.component.DuckieGridLayout
import team.duckie.app.android.util.compose.launch
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.component.QuackBasicTextArea
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackCircleTag
import team.duckie.quackquack.ui.component.QuackMediumToggleButton
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun ExamInformationScreen() = CoroutineScopeContent {
    val viewModel = LocalViewModel.current as CreateProblemViewModel
    val state = viewModel.state.collectAsStateWithLifecycle().value.examInformation
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            PrevAndNextTopAppBar(
                onLeadingIconClick = {
                    launch { viewModel.onClickArrowBack() }
                },
                onTrailingTextClick = { viewModel.navigateStep(CreateProblemStep.CreateProblem) },
                trailingTextEnabled = viewModel.isAllFieldsNotEmpty(),
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(space = 48.dp),
        ) { // TODO(EvergreenTree97): 컴포넌트 필요
            TitleAndComponent(stringResource = R.string.category_title) {
                DuckieGridLayout(items = state.categories) { index, item ->
                    QuackMediumToggleButton(
                        modifier = Modifier.size(
                            width = 102.dp,
                            height = 40.dp,
                        ),
                        text = item,
                        selected = state.categorySelection == index,
                        onClick = {
                            viewModel.onClickCategory(index)
                        },
                    )
                }
            }
            TitleAndComponent(stringResource = R.string.exam_area) {
                if (state.isExamAreaSelected) {
                    QuackCircleTag(
                        text = state.examArea,
                        trailingIcon = QuackIcon.Close,
                        isSelected = false,
                        onClick = { viewModel.onClickCloseTag(false) },
                    )
                } else {
                    QuackBasicTextField(
                        leadingIcon = QuackIcon.Search,
                        text = state.examArea,
                        onTextChanged = {
                            /*
                            * TODO(EvergreenTree97): Box로도 해당 영역 Clickable이 잡히지 않음
                            * 시험 영역 찾기 Screen으로 넘어가는 UX 개선사항 필요
                            * */
                            viewModel.navigateStep(CreateProblemStep.FindExamArea)
                        },
                        placeholderText = stringResource(id = R.string.find_exam_area),
                    )
                }
            }
            TitleAndComponent(stringResource = R.string.exam_title) {
                QuackBasicTextField(
                    text = state.examTitle,
                    onTextChanged = viewModel::setExamTitle,
                    placeholderText = stringResource(id = R.string.input_exam_title),
                    keyboardOptions = ImeActionNext,
                    keyboardActions = moveDownFocus(focusManager),
                )
            }

            TitleAndComponent(stringResource = R.string.exam_description) {
                QuackReviewTextArea(
                    modifier = Modifier.heightIn(140.dp),
                    text = state.examDescription,
                    onTextChanged = viewModel::setExamDescription,
                    placeholderText = stringResource(id = R.string.input_exam_description),
                    imeAction = ImeAction.Next,
                    keyboardActions = moveDownFocus(focusManager),
                )
            } // TODO(EvergreenTree97): 컴포넌트 필요
            TitleAndComponent(stringResource = R.string.certifying_statement) {
                QuackBasicTextArea(
                    text = state.certifyingStatement,
                    onTextChanged = viewModel::setCertifyingStatement,
                    placeholderText = stringResource(id = R.string.input_certifying_statement),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            launch {
                                lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                                focusManager.clearFocus()
                            }
                        },
                    ),
                )
            }
        }
    }
}
