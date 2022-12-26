/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.shared.ui.compose.UserFollowingLayout
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.kotlin.fastForEach
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

data class TestMaker(
    val coverUrl: String,
    val title: String,
    val examineeNumber: Int,
    val createAt: String,
    val owner: User,
) {
    data class User(
        val name: String,
        val profile: String,
    )
}

data class RecommendUserByTopic(
    val topic: String,
    val users: ImmutableList<RecommendUser>,
)

data class RecommendUser(
    val userId: Int,
    val profile: String,
    val name: String,
    val examineeNumber: Int,
    val createAt: String,
)

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun HomeRecommendFollowingTestScreen(
    modifier: Modifier = Modifier,
    followingTest: PersistentList<TestMaker>,
) {
    val vm = LocalViewModel.current as HomeViewModel
    val state = vm.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        vm.fetchRecommendFollowingTest()
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        item {
            HomeTopAppBar(
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

        itemsIndexed(
            items = followingTest,
        ) { _, maker ->
            TestCoverWithMaker(
                profile = maker.owner.profile,
                name = maker.owner.name,
                title = maker.title,
                takers = maker.examineeNumber,
                createAt = maker.createAt,
                onClickUserProfile = {
                    // TODO ("limsaehyun"): 추후에 유저의 profile로 이동 필요할 것 같음
                },
                onClickTestCover = {
                    // TODO ("limsaehyun"): 상세보기로 이동
                },
                cover = maker.coverUrl,
            )
        }

        item { }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun HomeRecommendFollowingScreen(
    modifier: Modifier = Modifier,
    recommendCategories: PersistentList<RecommendUserByTopic>,
) {
    val vm = LocalViewModel.current as HomeViewModel
    val state = vm.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        vm.fetchRecommendFollowing()
    }

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            HomeTopAppBar(
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(164.dp),
                contentAlignment = Alignment.Center,
            ) {
                QuackHeadLine2(
                    text = stringResource(id = R.string.home_following_initial_title),
                    align = TextAlign.Center,
                )
            }
        }

        items(
            items = recommendCategories,
            key = { categories ->
                categories.topic
            }
        ) { categories ->
            HomeFollowingInitialRecommendUsers(
                modifier = Modifier.padding(bottom = 16.dp),
                topic = categories.topic,
                recommendUser = categories.users,
                onClickFollowing = {
                    // TODO ("limsaehyun"): viewModel에서 Following 필요
                }
            )
        }
    }
}

@Composable
private fun HomeFollowingInitialRecommendUsers(
    modifier: Modifier = Modifier,
    topic: String,
    recommendUser: List<RecommendUser>,
    onClickFollowing: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        QuackDivider()
        QuackTitle2(
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 12.dp,
            ),
            text = topic,
        )
        recommendUser.fastForEach { user ->
            var following by remember { mutableStateOf(false) }

            UserFollowingLayout(
                userId = user.userId,
                profile = user.profile,
                examineeNumber = user.examineeNumber,
                name = user.name,
                createAt = user.createAt,
                isFollowing = following,
                onClickFollowing = {
                    following = !following
                    onClickFollowing(user.userId)
                },
            )
        }
    }
}

@Composable
private fun TestCoverWithMaker(
    modifier: Modifier = Modifier,
    cover: String,
    profile: String,
    title: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickTestCover: () -> Unit,
    onClickUserProfile: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .quackClickable {
                    onClickTestCover()
                },
            model = cover,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        TestMakerLayout(
            modifier = Modifier.padding(top = 12.dp),
            profile = profile,
            title = title,
            name = name,
            takers = takers,
            createAt = createAt,
            onClickUserProfile = onClickUserProfile,
        )
    }
}

private val HomeProfileSize: DpSize = DpSize(
    all = 24.dp,
)

// TODO(limsaehyun): 추후에 QuackQuack Shape 으로 대체 필요
private val HomeProfileShape: RoundedCornerShape = RoundedCornerShape(
    size = 16.dp
)

@Composable
private fun TestMakerLayout(
    modifier: Modifier = Modifier,
    profile: String,
    title: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickUserProfile: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackImage(
            src = profile,
            size = HomeProfileSize,
            shape = HomeProfileShape,
            onClick = {
                if (onClickUserProfile != null) {
                    onClickUserProfile()
                }
            },
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            QuackSubtitle2(
                text = title,
            )
            Row(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                QuackBody3(text = name)
                Spacer(modifier = Modifier.width(8.dp))
                QuackBody3(
                    text = "${stringResource(id = R.string.examinee)} $takers  ·  $createAt",
                    color = QuackColor.Gray2,
                )
            }
        }
    }
}
