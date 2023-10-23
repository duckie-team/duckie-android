/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.create.exam.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ImeSpacer
import team.duckie.app.android.common.compose.ui.quack.todo.QuackLazyVerticalGridTag
import team.duckie.app.android.common.compose.util.rememberUserInputState
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.feature.create.exam.R
import team.duckie.app.android.feature.create.exam.common.CreateProblemBottomLayout
import team.duckie.app.android.feature.create.exam.common.ExitAppBar
import team.duckie.app.android.feature.create.exam.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.create.exam.viewmodel.state.FindResultType
import team.duckie.app.android.feature.create.exam.viewmodel.state.SearchScreenData
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.defaultTextFieldIndicator
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

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
    var searchTextFieldValue by rememberUserInputState(
        defaultValue = state.textFieldValue,
        updateState = { textFieldValue ->
            viewModel.setTextFieldValue(textFieldValue = textFieldValue)
        },
    )

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
                trailingIcon = OutlinedGroup.Close,
                onClick = { viewModel.onClickCloseTag(it) },
                itemChunkedSize = 3,
            )
        }

        // TODO(riflockle7): 동작 확인 필요
        QuackDefaultTextField(
            // TODO(riflockle7): 꽥꽥 기능 제공 안함
            // leadingIcon = QuackIcon.Search,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                // 밑줄
                .defaultTextFieldIndicator(
                    colorGetter = { _, _, _ ->
                        return@defaultTextFieldIndicator QuackColor.Gray3
                    },
                )
                .focusRequester(focusRequester),
            value = searchTextFieldValue,
            onValueChange = { textFieldValue ->
                viewModel.setTextFieldValue(textFieldValue = textFieldValue)
            },
            style = QuackTextFieldStyle.Default,
            placeholderText = placeholderText,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (searchTextValidate(searchTextFieldValue, state)) {
                        coroutineScope.launch { viewModel.onClickSearchListHeader() }
                    }
                },
            ),
        )

        AnimatedVisibility(
            modifier = Modifier.padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            visible = state.textFieldValue.isNotEmpty(),
        ) {
            LazyColumn {
                item {
                    QuackText(
                        modifier = Modifier
                            .quackClickable(
                                onClick = {
                                    if (searchTextValidate(searchTextFieldValue, state)) {
                                        coroutineScope.launch {
                                            viewModel.onClickSearchListHeader()
                                        }
                                    }
                                },
                            )
                            .fillMaxWidth()
                            .padding(PaddingValues(vertical = 12.dp)),
                        typography = QuackTypography.Body1.change(color = QuackColor.Gray1),
                        text = stringResource(
                            id = R.string.add_also,
                            state.textFieldValue,
                        ),
                    )
                }
                itemsIndexed(
                    items = state.searchResults.fastMap { it.name },
                    key = { _, item -> item },
                ) { index: Int, item: String ->
                    QuackBody1(
                        modifier = Modifier
                            .quackClickable(
                                onClick = {
                                    if (searchTextValidate(searchTextFieldValue, state)) {
                                        coroutineScope.launch {
                                            viewModel.onClickSearchList(index)
                                        }
                                    }
                                },
                            )
                            .fillMaxWidth()
                            .padding(PaddingValues(vertical = 12.dp)),
                        text = item,
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
