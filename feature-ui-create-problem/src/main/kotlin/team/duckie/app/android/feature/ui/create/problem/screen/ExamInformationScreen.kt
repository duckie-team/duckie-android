/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.FadeAnimatedVisibility
import team.duckie.app.android.feature.ui.create.problem.common.ImeActionNext
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.common.TitleAndComponent
import team.duckie.app.android.feature.ui.create.problem.common.moveDownFocus
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.shared.ui.compose.DuckieGridLayout
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.launch
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackCircleTag
import team.duckie.quackquack.ui.component.QuackGrayscaleTextField
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun ExamInformationScreen() = CoroutineScopeContent {
    val viewModel = LocalViewModel.current as CreateProblemViewModel
    val state = viewModel.state.collectAsStateWithLifecycle().value.examInformation
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    LaunchedEffect(key1 = state.scrollPosition) {
        lazyListState.scrollToItem(index = state.scrollPosition)
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
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
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(space = 48.dp),
        ) {
            TitleAndComponent(stringResource = R.string.category_title) {
                AnimatedVisibility(visible = state.isCategoryLoading.not()) {
                    DuckieGridLayout(items = state.categories) { index, item ->
                        MediumButton(
                            text = item.name,
                            selected = state.categorySelection == index,
                            onClick = {
                                viewModel.onClickCategory(index)
                            },
                        )
                    }
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
                }
                FadeAnimatedVisibility(visible = !state.isExamAreaSelected) {
                    QuackBasicTextField(
                        modifier = Modifier.quackClickable {
                            viewModel.onClickExamArea(lazyListState.firstVisibleItemIndex)
                        },
                        leadingIcon = QuackIcon.Search,
                        text = state.examArea,
                        onTextChanged = {},
                        placeholderText = stringResource(id = R.string.search_exam_area_tag),
                        enabled = false,
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
                    modifier = Modifier
                        .heightIn(140.dp)
                        .focusRequester(focusRequester = focusRequester)
                        .onFocusChanged { state ->
                            viewModel.onExamAreaFocusChanged(state.isFocused)
                        },
                    text = state.examDescription,
                    onTextChanged = viewModel::setExamDescription,
                    placeholderText = stringResource(id = R.string.input_exam_description),
                    imeAction = ImeAction.Next,
                    keyboardActions = moveDownFocus(focusManager),
                    focused = state.examDescriptionFocused,
                )
            }
            TitleAndComponent(stringResource = R.string.certifying_statement) {
                QuackGrayscaleTextField(
                    modifier = Modifier.padding(bottom = 16.dp),
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
                    maxLength = viewModel.certifyingStatementMaxLength,
                    showCounter = true,
                )
            }
        }
    }
}

@Composable
private fun MediumButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    QuackSurface(
        modifier = modifier.size(
            width = 102.dp,
            height = 40.dp,
        ),
        backgroundColor = QuackColor.White,
        border = QuackBorder(
            color = when (selected) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            },
        ),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClick,
    ) {
        QuackText(
            modifier = Modifier.padding(all = 10.dp),
            text = text,
            style = when (selected) {
                true -> QuackTextStyle.Title2.change(
                    color = QuackColor.DuckieOrange,
                    textAlign = TextAlign.Center,
                )
                else -> QuackTextStyle.Body1.change(
                    color = QuackColor.Black,
                    textAlign = TextAlign.Center,
                )
            },
            singleLine = true,
        )
    }
}
