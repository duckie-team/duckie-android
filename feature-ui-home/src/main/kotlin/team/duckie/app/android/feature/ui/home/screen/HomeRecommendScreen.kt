/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSplashSlogan
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.component.QuackUnderlineHeadLine2
import team.duckie.quackquack.ui.modifier.quackClickable

private val HomeHorizontalPadding = PaddingValues(
    horizontal = 16.dp,
)

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalLifecycleComposeApi::class,
)
@Composable
internal fun HomeRecommendScreen(
    modifier: Modifier = Modifier,
) {
    val pageState = rememberPagerState()

    val vm = LocalViewModel.current as HomeViewModel
    val state = vm.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        vm.fetchRecommendations()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            HomeTopAppBar(
                modifier = Modifier
                    .padding(HomeHorizontalPadding)
                    .padding(bottom = 16.dp),
                selectedTabIndex = state.selectedTabIndex.index,
                onTabSelected = { step ->
                    vm.changedSelectedTab(
                        HomeStep.toStep(step)
                    )
                },
                onClickedEdit = {
                    // TODO("limsaehyun"): 수정 페이지로 이동 필요
                },
            )
        }

        item {
            HorizontalPager(
                count = state.jumbotrons.size,
                state = pageState,
            ) { page ->
                HomeRecommendJumbotronLayout(
                    modifier = Modifier.padding(HomeHorizontalPadding),
                    recommendItem = state.jumbotrons[page],
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

        items(items = state.recommendTopics) { item ->
            HomeTopicRecommendLayout(
                modifier = Modifier
                    .padding(
                        bottom = 60.dp,
                    ),
                title = item.title,
                tag = item.tag,
                recommendItems = item.items,
                onClicked = { }
            )
        }
    }
}

@Composable
private fun HomeRecommendJumbotronLayout(
    modifier: Modifier = Modifier,
    recommendItem: HomeState.HomeRecommendJumbotron,
    onStartClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = recommendItem.coverUrl,
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
private fun HomeTopicRecommendLayout(
    modifier: Modifier = Modifier,
    title: String,
    tag: String,
    recommendItems: PersistentList<HomeState.RecommendTopic.Test>,
    onClicked: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        QuackUnderlineHeadLine2(
            modifier = Modifier.padding(HomeHorizontalPadding),
            text = title,
            underlineTexts = persistentListOf(tag),
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {}

            items(items = recommendItems) { item ->
                Column(
                    modifier = Modifier
                        .quackClickable(
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
