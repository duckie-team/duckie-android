/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.feature.ui.home.component.DuckTestSmallCover
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.shared.ui.compose.DuckieGridLayout
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

enum class TagStep(
    val title: String,
    val index: Int,
) {
    DUCK_TEST(
        title = "덕질고사",
        index = 0,
    ),
    USER(
        title = "사용자",
        index = 1,
    );

    companion object {
        fun toStep(value: Int) = TagStep.values().first { it.index == value }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun HomeTagScreen(
    modifier: Modifier = Modifier,
) {
    val vm = LocalViewModel.current as HomeViewModel
    val state = vm.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        vm.fetchRecommendTag(state.selectedTag)
    }

    val tabTitles = TagStep.values().map {
        it.title
    }.toPersistentList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(QuackColor.White.composeColor)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onLeadingIconClick = {
                vm.changedHomeScreen(HomeStep.HomeRecommendScreen)
            },
            leadingText = state.selectedTag,
        )
        QuackMainTab(
            titles = tabTitles,
            selectedTabIndex = state.tagSelectedTabIndex.index,
            onTabSelected = { index ->
                vm.changedTagSelectedTab(TagStep.toStep(index))
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        DuckieGridLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            items = state.recommendByTags,
            columns = 2,
            verticalPadding = 48.dp,
        ) { _, item ->
            DuckTestSmallCover(
                modifier = Modifier.padding(),
                duckTestCoverItem = item,
                onClick = {
                    // TODO(limsaehyun): 상세보기로 이동
                },
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Preview
@Composable
fun PreviewHomeTagScreen() {
    HomeTagScreen()
}
