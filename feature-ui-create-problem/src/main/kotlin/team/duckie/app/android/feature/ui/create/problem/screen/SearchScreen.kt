/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.ExitAppBar
import team.duckie.app.android.feature.ui.create.problem.common.SearchResultText
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.FindResultType
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.SearchScreenData
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun SearchScreen(
    modifier: Modifier,
    viewModel: CreateProblemViewModel = activityViewModel(),
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val rootState = viewModel.collectAsState().value

    val state = when (rootState.findResultType) {
        FindResultType.ExamCategory -> rootState.examInformation.searchExamCategory
        FindResultType.Tag -> rootState.additionalInfo.searchTag
    }

    val title by remember {
        mutableStateOf(
            when (rootState.findResultType) {
                FindResultType.ExamCategory -> context.getString(R.string.find_exam_category)
                FindResultType.Tag -> context.getString(R.string.additional_information_tag_title)
            },
        )
    }
    val placeholderText by remember {
        mutableStateOf(
            when (rootState.findResultType) {
                FindResultType.ExamCategory -> context.getString(R.string.search_exam_category_placeholder)
                FindResultType.Tag -> context.getString(R.string.additional_information_tag_input_hint)
            },
        )
    }

    val multiSelectMode =
        remember(rootState.findResultType) { rootState.findResultType.isMultiMode() }

    val focusRequester = remember { FocusRequester() }
    var searchTextFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = state.textFieldValue,
                selection = TextRange(state.cursorPosition),
            ),
        )
    }

    BackHandler {
        viewModel.exitSearchScreen()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ExitAppBar(
            leadingText = title,
            onTrailingIconClick = { viewModel.exitSearchScreen() },
        )

        if (multiSelectMode) {
            QuackSingeLazyRowTag(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalSpace = 4.dp,
                items = state.results.fastMap { it.name },
                tagType = QuackTagType.Circle(QuackIcon.Close),
                onClick = { viewModel.onClickCloseTag(it) },
            )
        }

        QuackBasicTextField(
            modifier = Modifier
                .padding(16.dp)
                .focusRequester(focusRequester),
            leadingIcon = QuackIcon.Search,
            value = searchTextFieldValue,
            onValueChanged = { textFieldValue ->
                searchTextFieldValue = textFieldValue
                viewModel.setTextFieldValue(
                    textFieldValue = textFieldValue.text,
                    cursorPosition = textFieldValue.selection.end,
                )
            },
            placeholderText = placeholderText,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (searchTextValidate(searchTextFieldValue, state)) {
                        coroutineScope.launch {
                            viewModel.onClickSearchListHeader()
                            searchTextFieldValue = TextFieldValue()
                        }
                    }
                },
            ),
        )

        QuackAnimatedVisibility(
            modifier = Modifier.padding(16.dp),
            visible = state.textFieldValue.isNotEmpty(),
        ) {
            LazyColumn {
                item {
                    SearchResultText(
                        text = stringResource(
                            id = R.string.add_also,
                            state.textFieldValue,
                        ),
                        onClick = {
                            if (searchTextValidate(searchTextFieldValue, state)) {
                                coroutineScope.launch {
                                    viewModel.onClickSearchListHeader()
                                    searchTextFieldValue = TextFieldValue()
                                }
                            }
                        },
                    )
                }
                itemsIndexed(
                    items = state.searchResults,
                    key = { _, item -> item },
                ) { index: Int, item: String ->
                    SearchResultText(
                        text = item,
                        onClick = {
                            if (searchTextValidate(searchTextFieldValue, state)) {
                                coroutineScope.launch {
                                    viewModel.onClickSearchList(index)
                                    searchTextFieldValue = TextFieldValue()
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

/** 현재 입력한 내용이 추가하기 적합한 내용인지 확인한다. */
private fun searchTextValidate(
    searchTextFieldValue: TextFieldValue,
    state: SearchScreenData?,
): Boolean = searchTextFieldValue.text.isNotEmpty() &&
        state?.results?.fastMap { it.name }?.contains(searchTextFieldValue.text) != true
