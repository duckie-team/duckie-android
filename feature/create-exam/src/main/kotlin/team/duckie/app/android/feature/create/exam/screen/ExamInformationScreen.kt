/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class, ExperimentalDesignToken::class)
@file:Suppress("MagicNumber")

package team.duckie.app.android.feature.create.exam.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.DuckieGridLayout
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.ui.quack.todo.QuackSurface
import team.duckie.app.android.common.kotlin.takeBy
import team.duckie.app.android.feature.create.exam.R
import team.duckie.app.android.feature.create.exam.common.CreateProblemBottomLayout
import team.duckie.app.android.feature.create.exam.common.ImeActionNext
import team.duckie.app.android.feature.create.exam.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.create.exam.common.TitleAndComponent
import team.duckie.app.android.feature.create.exam.common.getCreateProblemMeasurePolicy
import team.duckie.app.android.feature.create.exam.common.moveDownFocus
import team.duckie.app.android.feature.create.exam.viewmodel.CreateProblemViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.material.icon.quackicon.outlined.Search
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackFilledTextField
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.QuackTextArea
import team.duckie.quackquack.ui.QuackTextAreaStyle
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.counter
import team.duckie.quackquack.ui.defaultTextFieldIcon
import team.duckie.quackquack.ui.defaultTextFieldIndicator
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.token.HorizontalDirection
import team.duckie.quackquack.ui.trailingIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

private const val TopAppBarLayoutId = "ExamInformationScreenTopAppBarLayoutId"
private const val ContentLayoutId = "ExamInformationScreenContentLayoutId"
private const val BottomLayoutId = "ExamInformationScreenBottomLayoutId"

private const val ExamTitleMaxLength = 12
private const val ExamDescriptionMaxLength = 30
private const val CertifyingStatementMaxLength = 16

@Composable
internal fun ExamInformationScreen(
    viewModel: CreateProblemViewModel = activityViewModel(),
    modifier: Modifier,
) {
    val activity = LocalContext.current as Activity
    val state = viewModel.collectAsState().value.examInformation
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()
    var createExamExitDialogVisible by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    BackHandler {
        if (!createExamExitDialogVisible) {
            createExamExitDialogVisible = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    LaunchedEffect(key1 = state.scrollPosition) {
        lazyListState.scrollToItem(index = state.scrollPosition)
    }

    Layout(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        measurePolicy = getCreateProblemMeasurePolicy(
            TopAppBarLayoutId,
            ContentLayoutId,
            BottomLayoutId,
        ),
        content = {
            PrevAndNextTopAppBar(
                modifier = Modifier.layoutId(TopAppBarLayoutId),
                trailingText = stringResource(id = R.string.next),
                onLeadingIconClick = {
                    if (!createExamExitDialogVisible) {
                        createExamExitDialogVisible = true
                    }
                },
            )

            LazyColumn(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .layoutId(ContentLayoutId),
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
                TitleAndComponent(stringResource = R.string.main_tag) {
                    if (state.isMainTagSelected) {
                        QuackTag(
                            text = state.mainTag,
                            style = QuackTagStyle.Outlined,
                            modifier = Modifier.trailingIcon(OutlinedGroup.Close) { viewModel.onClickCloseTag() },
                            selected = false,
                        ) {}
                    }
                    AnimatedVisibility(visible = !state.isMainTagSelected) {
                        // TODO(riflockle7): 동작 확인 필요
                        QuackDefaultTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultTextFieldIndicator(
                                    colorGetter = { _, _, _ ->
                                        return@defaultTextFieldIndicator QuackColor.Gray3
                                    },
                                )
                                .defaultTextFieldIcon(
                                    OutlinedGroup.Search,
                                    iconSize = 16.dp,
                                    direction = HorizontalDirection.Left,
                                )
                                .quackClickable(
                                    onClick = {
                                        viewModel.goToSearchMainTag(lazyListState.firstVisibleItemIndex)
                                    },
                                ),
                            value = state.mainTag,
                            onValueChange = {},
                            placeholderText = stringResource(id = R.string.search_main_tag_placeholder),
                            style = QuackTextFieldStyle.Default,
                            enabled = false,
                        )
                    }
                }
                TitleAndComponent(stringResource = R.string.exam_title) {
                    QuackDefaultTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultTextFieldIndicator(
                                colorGetter = { _, _, _ ->
                                    return@defaultTextFieldIndicator QuackColor.Gray3
                                },
                            )
                            .counter(
                                maxLength = 12,
                                highlightColor = QuackColor.Gray2,
                            ),
                        value = state.examTitle,
                        onValueChange = {
                            viewModel.setExamTitle(
                                it.takeBy(
                                    ExamTitleMaxLength,
                                    state.examTitle,
                                ),
                            )
                        },
                        style = QuackTextFieldStyle.Default,
                        placeholderText = stringResource(
                            id = R.string.input_exam_title,
                            ExamTitleMaxLength,
                        ),
                        keyboardOptions = ImeActionNext,
                        keyboardActions = moveDownFocus(focusManager),
                    )
                }
                TitleAndComponent(stringResource = R.string.exam_description) {
                    // TODO(riflockle7): 동작 확인 필요
                    QuackTextArea(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(140.dp)
                            .focusRequester(focusRequester = focusRequester)
                            .onFocusChanged { state ->
                                viewModel.onSearchTextFocusChanged(state.isFocused)
                            },
                        value = state.examDescription,
                        style = QuackTextAreaStyle.Default,
                        onValueChange = {
                            viewModel.setExamDescription(
                                it.takeBy(
                                    ExamDescriptionMaxLength,
                                    state.examDescription,
                                ),
                            )
                        },
                        placeholderText = stringResource(
                            id = R.string.input_exam_description,
                            ExamDescriptionMaxLength,
                        ),
                        keyboardOptions = ImeActionNext,
                        keyboardActions = moveDownFocus(focusManager),
                        // TODO(riflockle7): 꽥꽥에서 기능 제공 안함
                        // focused = state.examDescriptionFocused,
                    )
                }
                TitleAndComponent(stringResource = R.string.certifying_statement) {
                    QuackFilledTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .counter(
                                16,
                                highlightColor = QuackColor.Gray2,
                            )
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        value = state.certifyingStatement,
                        onValueChange = {
                            viewModel.setCertifyingStatement(
                                it.takeBy(
                                    CertifyingStatementMaxLength,
                                    state.certifyingStatement,
                                ),
                            )
                        },
                        style = QuackTextFieldStyle.FilledLarge,
                        placeholderText = stringResource(
                            id = R.string.input_certifying_statement,
                            CertifyingStatementMaxLength,
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
                                    focusManager.clearFocus()
                                }
                            },
                        ),
                    )
                }
            }

            // 최하단 Layout
            CreateProblemBottomLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId(BottomLayoutId),
                leftButtonText = stringResource(id = R.string.additional_information_next),
                nextButtonText = stringResource(id = R.string.next),
                nextButtonClick = {
                    coroutineScope.launch {
                        viewModel.getExamThumbnail()
                    }
                },
                isValidateCheck = viewModel::examInformationIsValidate,
            )
        },
    )

    DuckieDialog(
        title = stringResource(id = R.string.create_problem_exit_dialog_title),
        message = stringResource(id = R.string.create_problem_exit_dialog_message),
        visible = createExamExitDialogVisible,
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = { createExamExitDialogVisible = false },
        rightButtonText = stringResource(id = R.string.ok),
        rightButtonOnClick = {
            createExamExitDialogVisible = false
            activity.finish()
        },
        onDismissRequest = { createExamExitDialogVisible = false },
    )
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
        border = BorderStroke(
            width = 1.dp,
            brush = when (selected) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Gray3
            }.toBrush(),
        ),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = onClick,
    ) {
        QuackText(
            modifier = Modifier.padding(all = 10.dp),
            text = text,
            typography = when (selected) {
                true -> QuackTypography.Title2.change(
                    color = QuackColor.DuckieOrange,
                    textAlign = TextAlign.Center,
                )

                else -> QuackTypography.Body1.change(
                    color = QuackColor.Black,
                    textAlign = TextAlign.Center,
                )
            },
            singleLine = true,
        )
    }
}
