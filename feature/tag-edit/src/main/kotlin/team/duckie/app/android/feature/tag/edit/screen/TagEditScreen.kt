/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.tag.edit.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
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
import team.duckie.app.android.common.compose.ui.BackPressedHeadLine2TopAppBar
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.FavoriteTagSection
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.compose.ui.domain.DuckieTagAddBottomSheet
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.tag.edit.R
import team.duckie.app.android.feature.tag.edit.viewmodel.TagEditState
import team.duckie.app.android.feature.tag.edit.viewmodel.TagEditViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText

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
) {
    val activity = LocalContext.current as Activity
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    BackHandler {
        if (sheetState.isVisible) {
            coroutineScope.launch { sheetState.hide() }
        } else {
            activity.finish()
        }
    }

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
                BackPressedHeadLine2TopAppBar(
                    title = stringResource(R.string.title),
                    onBackPressed = activity::finish,
                    trailingContent = {
                        QuackText(
                            modifier = Modifier
                                .quackClickable(onClick = onEditFinishClick),
                            text = stringResource(R.string.edit_finish),
                            typography = QuackTypography.Subtitle.change(
                                color = QuackColor.DuckieOrange,
                            ),
                            singleLine = true,
                        )
                    }
                )

                // 내 관심 태그 영역
                FavoriteTagSection(
                    modifier = Modifier.padding(vertical = 20.dp),
                    title = stringResource(id = R.string.my_favorite_tag),
                    horizontalPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    trailingIcon = OutlinedGroup.Close,
                    onTrailingClick = onTrailingClick,
                    tags = state.myTags.map { it.name }.toPersistentList(),
                    emptySection = {},
                    onTagClick = {},
                    addButtonTitle = stringResource(id = R.string.tag_edit_add_favorite_tag),
                    onAddTagClick = {
                        coroutineScope.launch { sheetState.show() }
                    },
                )
            }
        },
    )
}
