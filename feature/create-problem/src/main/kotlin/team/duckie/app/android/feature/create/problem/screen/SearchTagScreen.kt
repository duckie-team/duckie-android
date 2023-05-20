/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.problem.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.create.problem.R
import team.duckie.app.android.feature.create.problem.common.CreateProblemBottomLayout
import team.duckie.app.android.feature.create.problem.common.ExitAppBar
import team.duckie.app.android.feature.create.problem.common.SearchResultText
import team.duckie.app.android.feature.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.create.problem.viewmodel.state.FindResultType
import team.duckie.app.android.feature.create.problem.viewmodel.state.SearchScreenData
import team.duckie.app.android.shared.ui.compose.ImeSpacer
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackLazyVerticalGridTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.icon.QuackIcon

private const val MaximumSubTagCount = 5

@Composable
internal fun SearchTagScreen(
    modifier: Modifier,
    viewModel: CreateProblemViewModel = activityViewModel(),
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val rootState = viewModel.collectAsState().value

    val state = when (rootState.findResultType) {
        FindResultType.MainTag -> rootState.examInformation.searchMainTag
        FindResultType.SubTags -> rootState.additionalInfo.searchSubTags
    }

    val title by remember {
        mutableStateOf(
            when (rootState.findResultType) {
                FindResultType.MainTag -> context.getString(R.string.find_main_tag)
                FindResultType.SubTags -> context.getString(R.string.additional_information_sub_tags_title)
            },
        )
    }
    val placeholderText by remember {
        mutableStateOf(
            when (rootState.findResultType) {
                FindResultType.MainTag -> context.getString(R.string.search_main_tag_placeholder)
                FindResultType.SubTags -> context.getString(R.string.additional_information_sub_tags_placeholder)
            },
        )
    }

    val multiSelectMode =
        remember(rootState.findResultType) { rootState.findResultType.isMultiMode() }

    val focusRequester = remember { FocusRequester() }
    val searchTextFieldValue = remember(state.textFieldValue) { state.textFieldValue }

    BackHandler {
        viewModel.exitSearchScreen()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier.navigationBarsPadding()) {
        ExitAppBar(
            leadingText = title,
            onTrailingIconClick = { viewModel.exitSearchScreen() },
        )

        if (multiSelectMode) {
            // TODO(riflockle7): 추후 꽥꽥에서, 전체 너비만큼 태그 Composable 을 넣을 수 있는 Composable 적용 필요
            QuackLazyVerticalGridTag(
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
                horizontalSpace = 4.dp,
                items = state.results.fastMap { it.name },
                tagType = QuackTagType.Circle(QuackIcon.Close),
                onClick = { viewModel.onClickCloseTag(it) },
                itemChunkedSize = 3,
            )
        }

        QuackBasicTextField(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                .focusRequester(focusRequester),
            leadingIcon = QuackIcon.Search,
            text = searchTextFieldValue,
            onTextChanged = { textFieldValue ->
                viewModel.setTextFieldValue(textFieldValue = textFieldValue)
            },
            placeholderText = placeholderText,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (searchTextValidate(searchTextFieldValue, state)) {
                        coroutineScope.launch { viewModel.onClickSearchListHeader() }
                    }
                },
            ),
        )

        QuackAnimatedVisibility(
            modifier = Modifier.padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
            ),
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
                                coroutineScope.launch { viewModel.onClickSearchListHeader() }
                            }
                        },
                    )
                }
                itemsIndexed(
                    items = state.searchResults.fastMap { it.name },
                    key = { _, item -> item },
                ) { index: Int, item: String ->
                    SearchResultText(
                        text = item,
                        onClick = {
                            if (searchTextValidate(searchTextFieldValue, state)) {
                                coroutineScope.launch { viewModel.onClickSearchList(index) }
                            }
                        },
                    )
                }
            }
        }

        if (multiSelectMode) {
            Spacer(modifier = Modifier.weight(1f))

            CreateProblemBottomLayout(
                modifier = Modifier,
                nextButtonText = stringResource(id = R.string.complete),
                nextButtonClick = {
                    viewModel.exitSearchScreen(true)
                },
                isValidateCheck = {
                    return@CreateProblemBottomLayout state.results.isNotEmpty()
                },
            )

            ImeSpacer()
        }
    }
}

/** 현재 입력한 내용이 추가하기 적합한 내용인지 확인한다. */
private fun searchTextValidate(
    searchTextFieldValue: String,
    state: SearchScreenData?,
): Boolean = searchTextFieldValue.isNotEmpty() &&
        (state?.results?.count() ?: 0) < MaximumSubTagCount &&
        state?.results?.fastMap { it.name }?.contains(searchTextFieldValue) != true
