/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.quack.todo.QuackOutLinedSingeLazyRowTag
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun FavoriteTagSection(
    isLoading: Boolean,
    title: String,
    emptySection: @Composable ColumnScope.() -> Unit,
    tags: ImmutableList<Tag>,
    onClickTag: (String) -> Unit,
) {
    val tagList = remember(tags) { tags.map { it.name }.toList() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(13.5.dp),
    ) {
        QuackTitle2(text = title)

        if (tags.isEmpty()) {
            emptySection()
        } else {
            // TODO(riflockle7): quack v2 에서 대체할 수 있는 내용 찾기
            QuackOutLinedSingeLazyRowTag(
                modifier = Modifier.skeleton(isLoading),
                items = tagList,
                trailingIcon = null,
                onClick = { index -> onClickTag(tagList[index]) },
            )
        }
    }
}
