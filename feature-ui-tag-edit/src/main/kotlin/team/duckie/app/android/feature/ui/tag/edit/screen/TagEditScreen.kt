/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.tag.edit.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.tag.edit.R
import team.duckie.app.android.feature.ui.tag.edit.viewmodel.TagEditState
import team.duckie.app.android.feature.ui.tag.edit.viewmodel.TagEditViewModel
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.FavoriteTagSection
import team.duckie.app.android.shared.ui.compose.LoadingScreen
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType

@Composable
internal fun TagEditScreen(
    vm: TagEditViewModel = activityViewModel(),
    modifier: Modifier,
) {
    val state = vm.collectAsState().value

    when (state) {
        is TagEditState.Loading -> LoadingScreen(
            modifier = modifier,
            initState = vm::initState,
        )

        is TagEditState.Success -> TagEditSuccessScreen(
            modifier = modifier,
            state = state,
            onAddTagClick = vm::onAddTagClick,
            onTagClick = vm::onTagClick,
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
    onAddTagClick: () -> Unit,
    onTagClick: (String) -> Unit,
) {
    Column {
        FavoriteTagSection(
            modifier = modifier,
            title = stringResource(id = R.string.my_favorite_tag),
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
            tags = state.myTags.map { it.name }.toPersistentList(),
            emptySection = {
                QuackLargeButton(
                    type = QuackLargeButtonType.Compact,
                    text = stringResource(id = R.string.add_favorite_tag),
                    onClick = onAddTagClick,
                )
            },
            onTagClick = onTagClick,
            addButtonTitle = stringResource(id = R.string.add_favorite_tag),
            onAddTagClick = onAddTagClick,
        )
    }
}
