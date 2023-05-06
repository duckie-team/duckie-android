/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose.screen

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.app.android.shared.ui.compose.ImeSpacer
import team.duckie.app.android.shared.ui.compose.R
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackLazyVerticalGridTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
fun SearchTagScreen(
    modifier: Modifier,
    title: String,
    placeholderText: String,
    multiSelectMode: Boolean,
    onCloseClick: () -> Unit,
    onBackPressed: () -> Unit,
    onTagClick: (index: Int) -> Unit = {},
    onClickCloseTag: (index: Int) -> Unit = {},
    onClickSearchListHeader: (tag: String) -> Unit = {},
    onClickSearchList: (index: Int) -> Unit = {},
    onTextChanged: (newSearchTextValue: String) -> Unit,
    onSearchTextValidate: (searchTextValue: String) -> Boolean = { true },
    tags: ImmutableList<String> = persistentListOf(),
    searchResults: ImmutableList<String> = persistentListOf(),
    bottomLayout: @Composable (() -> Unit)? = null,
) {
    if (tags.isNotEmpty()) {
        require(onTagClick != {})
        require(onClickCloseTag != {})
    }

    val coroutineScope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }
    val searchTextFieldValue = remember { mutableStateOf("") }

    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier.navigationBarsPadding()) {
        QuackTopAppBar(
            leadingText = title,
            trailingIcon = QuackIcon.Close,
            onTrailingIconClick = onCloseClick,
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
                items = tags,
                tagType = QuackTagType.Circle(QuackIcon.Close),
                onClick = { onTagClick(it) },
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
            text = searchTextFieldValue.value,
            onTextChanged = { textFieldValue ->
                searchTextFieldValue.value = textFieldValue
                onTextChanged(textFieldValue)
            },
            placeholderText = placeholderText,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (onSearchTextValidate(searchTextFieldValue.value)) {
                        coroutineScope.launch {
                            onClickSearchListHeader(searchTextFieldValue.value)
                        }
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
            visible = searchTextFieldValue.value.isNotEmpty(),
        ) {
            LazyColumn {
                if (onSearchTextValidate(searchTextFieldValue.value)) {
                    item {
                        SearchTagItemScreen(
                            text = stringResource(
                                id = R.string.tag_header_title,
                                searchTextFieldValue.value,
                            ),
                            onClick = {
                                if (onSearchTextValidate(searchTextFieldValue.value)) {
                                    coroutineScope.launch {
                                        onClickSearchListHeader(searchTextFieldValue.value)
                                    }
                                }
                            },
                        )
                    }
                }

                itemsIndexed(
                    items = searchResults,
                    key = { _, item -> item },
                ) { index: Int, item: String ->
                    SearchTagItemScreen(
                        text = item,
                        onClick = {
                            coroutineScope.launch { onClickSearchList(index) }
                        },
                    )
                }
            }
        }

        if (multiSelectMode) {
            Spacer(modifier = Modifier.weight(1f))

            bottomLayout?.let { it() }

            ImeSpacer()
        }
    }
}

@Composable
private fun SearchTagItemScreen(
    text: String,
    onClick: () -> Unit,
) {
    QuackBody1(
        modifier = Modifier.fillMaxWidth(),
        padding = PaddingValues(vertical = 12.dp),
        text = text,
        onClick = onClick,
    )
}
