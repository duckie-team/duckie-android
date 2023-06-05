/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.tag.edit.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.FavoriteTagSection
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.compose.ui.domain.DuckieTagAddBottomSheet
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.tag.edit.R
import team.duckie.app.android.feature.tag.edit.viewmodel.TagEditState
import team.duckie.app.android.feature.tag.edit.viewmodel.TagEditViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
internal fun TagEditScreen(
    vm: TagEditViewModel = activityViewModel(),
    modifier: Modifier,
) {
    when (val state = vm.collectAsState().value) {
        is TagEditState.Loading -> LoadingScreen(
            modifier = modifier,
            initState = vm::initState,
        )

        is TagEditState.Success -> TagEditSuccessScreen(
            modifier = modifier,
            state = state,
            onEditFinishClick = vm::onEditFinishClick,
            onTrailingClick = vm::onTrailingClick,
            addNewTags = vm::addNewTags,
            requestAddTag = vm::requestNewTag,
            onTagClick = vm::onTrailingClick,
        )

        is TagEditState.Error -> ErrorScreen(
            modifier = modifier,
            onRetryClick = vm::initState,
        )
    }
}

@Composable
fun TagEditSuccessScreen(
    modifier: Modifier,
    state: TagEditState.Success,
    onEditFinishClick: () -> Unit,
    onTrailingClick: (Int) -> Unit,
    addNewTags: (List<Tag>) -> Unit,
    requestAddTag: suspend (String) -> Tag?,
    onTagClick: (Int) -> Unit,
) {
    val activity = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    DuckieTagAddBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { newAddedTags, clearAction ->
            coroutineScope.launch {
                addNewTags(newAddedTags)
                clearAction()
                sheetState.hide()
            }
        },
        requestAddTag = requestAddTag,
        content = {
            Column(modifier = modifier) {
                // 상단 탭바
                QuackTopAppBar(
                    leadingIcon = QuackIcon.ArrowBack,
                    leadingText = stringResource(R.string.title),
                    onLeadingIconClick = activity::finish,
                    trailingContent = {
                        QuackSubtitle(
                            modifier = Modifier
                                .then(Modifier) // prevent Modifier.Companion
                                .quackClickable(
                                    rippleEnabled = false,
                                    onClick = onEditFinishClick,
                                )
                                .padding(
                                    vertical = 4.dp,
                                    horizontal = 16.dp,
                                ),
                            text = stringResource(R.string.edit_finish),
                            color = QuackColor.DuckieOrange,
                            singleLine = true,
                        )
                    },
                )

                // 내 관심 태그 영역
                FavoriteTagSection(
                    modifier = Modifier.padding(vertical = 20.dp),
                    title = stringResource(id = R.string.my_favorite_tag),
                    horizontalPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    trailingIcon = QuackIcon.Close,
                    onTrailingClick = onTrailingClick,
                    tags = state.myTags.map { it.name }.toPersistentList(),
                    emptySection = {},
                    singleLine = false,
                    onTagClick = onTagClick,
                    addButtonTitle = stringResource(id = R.string.tag_edit_add_favorite_tag),
                    onAddTagClick = {
                        coroutineScope.launch { sheetState.show() }
                    },
                )
            }
        },
    )
}
