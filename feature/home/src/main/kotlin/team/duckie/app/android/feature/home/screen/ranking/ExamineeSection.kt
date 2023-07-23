/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.screen.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import team.duckie.app.android.common.compose.itemsIndexedPagingKey
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.home.viewmodel.ranking.RankingViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun ExamineeSection(
    viewModel: RankingViewModel,
    lazyListState: LazyListState,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val examinees = viewModel.userRankings.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        itemsIndexed(
            items = examinees,
            key = itemsIndexedPagingKey(
                items = examinees,
                key = { examinees[it]?.id },
            ),
        ) { index, user ->
            ExamineeContent(
                rank = index + 1,
                user = user ?: User.empty(),
                isLoading = examinees.loadState.refresh == LoadState.Loading || state.isPagingDataLoading,
                onClick = viewModel::clickUser,
            )
        }
    }
}

@Composable
private fun ExamineeContent(
    rank: Int,
    user: User,
    isLoading: Boolean,
    onClick: (Int) -> Unit,
) = with(user) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .quackClickable(
                    onClick = { onClick(user.id) },
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
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
                QuackSubtitle(
                    modifier = Modifier
                        .widthIn(min = 28.dp)
                        .skeleton(isLoading),
                    text = "${rank}등",
                )
                Spacer(space = 12.dp)
                QuackProfileImage(
                    modifier = Modifier.skeleton(isLoading),
                    profileUrl = profileImageUrl,
                    size = DpSize(all = 44.dp),
                )
                Spacer(space = 8.dp)
                QuackTitle2(
                    modifier = Modifier.skeleton(isLoading),
                    text = nickname,
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(end = 16.dp)
                    .skeleton(isLoading),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                duckPower?.tier?.let { QuackBody2(text = it) }
                duckPower?.tag?.let {
                    QuackBody2(text = "|")
                    QuackBody2(text = it.name)
                }
            }
        }
        Divider(color = QuackColor.Gray4.composeColor)
    }
}
