package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.collections.immutable.PersistentList
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.common.RecommendUserProfile
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

data class Maker(
    val cover: String,
    val profile: String,
    val title: String,
    val name: String,
    val takers: Int,
    val createAt: String,
)

data class RecommendCategories(
    val topic: String,
    val users: PersistentList<RecommendUser>,
)

data class RecommendUser(
    val userId: Int = 0,
    val profile: String,
    val name: String,
    val taker: Int,
    val createAt: String,
)

private val HomeFollowingPadding: Dp = 24.dp

@Composable
internal fun HomeFollowingScreen(
    modifier: Modifier = Modifier,
    followingTest: PersistentList<Maker>,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(HomeFollowingPadding)
    ) {
        itemsIndexed(followingTest) { index, maker ->
            TestCoverWithMaker(
                profile = maker.profile,
                title = maker.title,
                name = maker.name,
                takers = maker.takers,
                createAt = maker.createAt,
                onClickUserProfile = {},
                onClickTestCover = {},
                cover = maker.cover,
            )

            if (index == followingTest.size - 1) {
                Spacer(modifier = Modifier.height(HomeFollowingPadding))
            }
        }
    }
}

@Composable
internal fun HomeFollowingInitialScreen(
    modifier: Modifier = Modifier,
    recommendCategories: PersistentList<RecommendCategories>,
) {
    LazyColumn(
        modifier = modifier,
    ) {
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

        items(recommendCategories) { categories ->
            HomeRecommendUsers(
                topic = categories.topic,
                recommendUser = categories.users,
                onClickFollowing = {
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HomeRecommendUsers(
    topic: String,
    recommendUser: List<RecommendUser>,
    onClickFollowing: (Int) -> Unit,
) {
    QuackDivider()

    Spacer(modifier = Modifier.height(24.dp))

    QuackTitle2(
        text = topic,
    )

    Spacer(modifier = Modifier.height(12.dp))

    recommendUser.map { user ->
        var following by remember { mutableStateOf(false) }

        RecommendUserProfile(
            profile = user.profile,
            name = user.name,
            takers = user.taker,
            createAt = user.createAt,
            isFollowing = following,
            onClickFollowing = {
                following = !following
                onClickFollowing(user.userId)
            },
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun TestCoverWithMaker(
    cover: String,
    profile: String,
    title: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickTestCover: () -> Unit,
    onClickUserProfile: () -> Unit,
) {
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .quackClickable {
                    onClickTestCover()
                },
            model = cover,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(12.dp))

        TestMakerContent(
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
private fun TestMakerContent(
    profile: String,
    title: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickUserProfile: (() -> Unit)? = null,
) {
    Row(
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

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            QuackSubtitle2(
                text = title,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                QuackBody3(text = name)

                Spacer(modifier = Modifier.width(8.dp))

                QuackBody3(
                    text = "${stringResource(id = R.string.taker)} $takers  ·  $createAt",
                    color = QuackColor.Gray2,
                )
            }
        }
    }
}
