/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.tag.edit.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.tag.edit.R
import team.duckie.app.android.feature.ui.tag.edit.viewmodel.TagEditState
import team.duckie.app.android.feature.ui.tag.edit.viewmodel.TagEditViewModel
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.FavoriteTagSection
import team.duckie.app.android.shared.ui.compose.LoadingScreen
import team.duckie.app.android.shared.ui.compose.screen.SearchTagScreen
import team.duckie.app.android.util.compose.activityViewModel
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
            onAddTagClick = vm::onAddTagClick,
            onTagClick = vm::onTagClick,
        )

        is TagEditState.AddTag -> SearchTagScreen(
            modifier = modifier,
            title = stringResource(id = R.string.add_tag_title),
            placeholderText = stringResource(id = R.string.add_tag_placeholder),
            multiSelectMode = false,
            onCloseClick = vm::onAddFinishClick,
            onBackPressed = vm::onAddFinishClick,
            onClickSearchListHeader = vm::onSearchTagHeaderClick,
            onClickSearchList = { index -> vm.onSearchTagClick(index) },
            onTextChanged = { newSearchTextValue -> vm.onSearchTextChanged(newSearchTextValue) },
            onSearchTextValidate = { searchTextValue -> vm.onSearchTextValidate(searchTextValue) },
            searchResults = state.searchResults.map { it.name }.toImmutableList(),
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
    onAddTagClick: () -> Unit,
    onTagClick: (Int) -> Unit,
) {
    val activity = LocalContext.current as Activity

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
            onAddTagClick = onAddTagClick,
        )
    }
}
