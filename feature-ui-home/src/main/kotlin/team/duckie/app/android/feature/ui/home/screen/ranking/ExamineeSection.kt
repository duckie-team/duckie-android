/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.home.screen.ranking

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.home.screen.ranking.viewmodel.RankingViewModel
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.shared.ui.compose.skeleton
import team.duckie.app.android.util.compose.itemsIndexedPagingKey
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.shape.SquircleShape
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun ExamineeSection(
    viewModel: RankingViewModel,
    lazyListState: LazyListState,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val examinees = viewModel.userRankings.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.getUserRankings()
    }

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
            )
        }
    }
}

@Composable
private fun ExamineeContent(
    rank: Int,
    user: User,
    isLoading: Boolean,
) = with(user) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                QuackImage(
                    modifier = Modifier.skeleton(isLoading),
                    src = profileImageUrl,
                    shape = SquircleShape,
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
                favoriteTags?.let {
                    QuackBody2(text = "|")
                    QuackBody2(text = it.first().name)
                }
            }
        }
        Divider(color = QuackColor.Gray4.composeColor)
    }
}
