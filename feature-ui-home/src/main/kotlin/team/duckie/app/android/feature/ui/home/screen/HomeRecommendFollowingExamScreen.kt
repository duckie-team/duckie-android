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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.HomeTopAppBar
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.shared.ui.compose.skeleton
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.shape.SquircleShape
import team.duckie.quackquack.ui.util.DpSize

internal const val ThumbnailRatio = 4f / 3f

private val HomeProfileSize: DpSize = DpSize(
    all = 24.dp,
)

@Composable
internal fun HomeRecommendFollowingExamScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = activityViewModel(),
) {
    val state = vm.collectAsState().value
    val toast = rememberToast()
    val homeLoadingMypageToastMessage = stringResource(id = R.string.home_loading_mypage_toast)

    LaunchedEffect(Unit) {
        vm.fetchRecommendFollowingExam()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item {
            HomeTopAppBar(
                selectedTabIndex = state.homeSelectedIndex.index,
                onTabSelected = { step ->
                    vm.changedHomeScreen(HomeStep.toStep(step))
                },
                onClickedCreate = {
                    vm.navigateToCreateProblem()
                },
            )
        }

        itemsIndexed(
            items = state.recommendFollowingExam,
        ) { _, maker ->
            TestCoverWithMaker(
                profile = maker.owner.profileImgUrl,
                name = maker.owner.nickname,
                title = maker.title,
                tier = maker.owner.tier,
                favoriteTag = maker.owner.favoriteTag,
                onClickUserProfile = {
                    // TODO(limsaehyun): 마이페이지로 이동
                    toast.invoke(homeLoadingMypageToastMessage)
                },
                onClickTestCover = {
                    vm.navigateToHomeDetail(maker.examId)
                },
                cover = maker.coverUrl,
                isLoading = state.isHomeRecommendFollowingExamLoading,
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
    tier: String,
    favoriteTag: String,
    onClickTestCover: () -> Unit,
    onClickUserProfile: () -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(ThumbnailRatio)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .quackClickable {
                    onClickTestCover()
                }
                .skeleton(isLoading),
            model = cover,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        TestMakerLayout(
            modifier = Modifier.padding(top = 12.dp),
            profile = profile,
            title = title,
            name = name,
            tier = tier,
            favoriteTag = favoriteTag,
            onClickUserProfile = onClickUserProfile,
            isLoading = isLoading,
        )
    }
}

@Composable
private fun TestMakerLayout(
    modifier: Modifier = Modifier,
    profile: String,
    title: String,
    name: String,
    tier: String,
    favoriteTag: String,
    isLoading: Boolean,
    onClickUserProfile: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackImage(
            modifier = Modifier.skeleton(isLoading),
            src = profile,
            size = HomeProfileSize,
            shape = SquircleShape,
            onClick = {
                if (onClickUserProfile != null) {
                    onClickUserProfile()
                }
            },
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            QuackSubtitle2(
                modifier = Modifier.skeleton(isLoading),
                text = title,
            )
            Row(modifier = Modifier.padding(top = 4.dp)) {
                QuackBody3(
                    modifier = Modifier.skeleton(isLoading),
                    text = name,
                )
                Spacer(modifier = Modifier.width(8.dp))
                QuackBody3(
                    modifier = Modifier.skeleton(isLoading),
                    text = "$tier · $favoriteTag",
                    color = QuackColor.Gray2,
                )
            }
        }
    }
}
