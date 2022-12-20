/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.home.R
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSplashSlogan
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.component.QuackUnderlineHeadLine2
import team.duckie.quackquack.ui.modifier.quackClickable

internal data class HomeRecommendItem(
    val image: String,
    val title: String,
    val content: String,
    val buttonContent: String,
)

internal data class TopicRecommendItem(
    val title: String,
    val tag: String,
    val items: PersistentList<DuckTest>
) {
    data class DuckTest(
        val coverImg: String,
        val nickname: String,
        val title: String,
        val examineeNumber: Int,
        val recommendId: Int,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun HomeRecommendScreen(
    modifier: Modifier = Modifier,
    homeRecommendItems: PersistentList<HomeRecommendItem>,
    topicRecommendItems: PersistentList<TopicRecommendItem>
) {
    val pageState = rememberPagerState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            HorizontalPager(
                count = homeRecommendItems.size,
                state = pageState,
            ) { page ->
                HomeRecommendContentScreen(
                    recommendItem = homeRecommendItems[page],
                    onStartClicked = {
                        // TODO ("limsaehyun"): 상세보기로 이동 필요
                    }
                )
            }
        }

        item {
            HorizontalPagerIndicator(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 60.dp),
                pagerState = pageState,
            )
        }

        items(items = topicRecommendItems) { item ->
            HomeTopicRecommend(
                modifier = Modifier
                    .padding(bottom = 60.dp),
                title = item.title,
                tag = item.tag,
                recommendItems = item.items,
                onClicked = { }
            )
        }
    }
}

@Composable
private fun HomeRecommendContentScreen(
    recommendItem: HomeRecommendItem,
    onStartClicked: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = recommendItem.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Spacer(modifier = Modifier.height(24.dp))
        QuackSplashSlogan(
            text = recommendItem.title,
        )
        Spacer(modifier = Modifier.height(12.dp))
        QuackBody1(
            text = recommendItem.content,
            align = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        QuackLargeButton(
            type = QuackLargeButtonType.Fill,
            text = recommendItem.buttonContent,
            onClick = onStartClicked,
            enabled = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        QuackBody3(
            text = stringResource(
                id = R.string.home_volume_control_message,
            )
        )
    }
}

@Composable
private fun HomeTopicRecommend(
    modifier: Modifier,
    title: String,
    tag: String,
    recommendItems: PersistentList<TopicRecommendItem.DuckTest>,
    onClicked: (Int) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        QuackUnderlineHeadLine2(
            text = title,
            underlineTexts = persistentListOf(tag),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = recommendItems) { item ->
                Column(
                    modifier = Modifier.quackClickable(
                        rippleEnabled = false,
                    ) {
                        onClicked(item.recommendId)
                    }
                ) {
                    AsyncImage(
                        modifier = Modifier.size(158.dp, 116.dp),
                        model = item.coverImg,
                        contentDescription = null,
                    )
                    QuackBody2(
                        modifier = Modifier.padding(top = 8.dp),
                        text = item.nickname,
                    )
                    QuackTitle2(
                        modifier = Modifier.padding(top = 4.dp),
                        text = item.title,
                    )
                    QuackBody2(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "${stringResource(id = R.string.examinee)} ${item.examineeNumber}",
                    )
                }
            }
        }
    }
}
