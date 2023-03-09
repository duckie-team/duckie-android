/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.shared.ui.compose.RowSpacer
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun ExamineeSection(
    users: ImmutableList<User> = persistentListOf(),
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = users,
            key = { _, user -> user.id }
        ) { index, user ->
            ExamineeContent(rank = index + 1, user = user)
        }
    }

}

@Composable
private fun ExamineeContent(
    rank: Int,
    user: User,
) = with(user) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .padding(
                    start = 16.dp,
                    end = 8.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackSubtitle(text = "${rank}등")
            RowSpacer(space = 12.dp)
            QuackImage(
                src = profileImageUrl,
                size = DpSize(all = 44.dp),
            )
            QuackTitle2(text = nickname)
        }
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            QuackBody2(text = "덕력 $duckPower%")
            favoriteTags?.let {
                QuackBody2(text = "|")
                QuackBody2(text = "${it.first()}")
            }
        }
    }
}
