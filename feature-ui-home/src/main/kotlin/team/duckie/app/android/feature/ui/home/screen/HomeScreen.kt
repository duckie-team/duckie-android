package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.common.HomeTextTab
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.util.DpSize

private val FakeHomeRecommendItem =
    persistentListOf(
        HomeRecommendItem(
            title = "제 1회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        ),
        HomeRecommendItem(
            title = "제 2회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        ),
        HomeRecommendItem(
            title = "제 3회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        )
    )

private val FakeHomeTopicRecommendItem =
    persistentListOf(
        TopicRecommendItem(
            title = "쿠키좀 쿠워봤어?\n#웹툰 퀴즈",
            tag = "#웹툰",
            items = persistentListOf(
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "외모지상주의 잘 알아?",
                    takers = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    takers = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    takers = 20,
                    recommendId = 1,
                )
            )
        ),
        TopicRecommendItem(
            title = "쿠키좀 쿠워봤어?\n#웹툰 퀴즈",
            tag = "#웹툰",
            items = persistentListOf(
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "외모지상주의 잘 알아?",
                    takers = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    takers = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    takers = 20,
                    recommendId = 1,
                )
            )
        )
    )

private val FakeHomeInitRecommendCategories = persistentListOf(
    RecommendCategories(
        topic = "연예인",
        users = persistentListOf(
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
        ),
    ),
    RecommendCategories(
        topic = "영화",
        users = persistentListOf(
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                taker = 20,
                createAt = "1일 전",
            ),
        ),
    )
)

private val FakeFollowingTest = persistentListOf(
    Maker(
        profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        title = "제 1회 도로 패션영역",
        name = "닉네임",
        takers = 30,
        createAt = "1일 전",
    ),
    Maker(
        profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        title = "제 1회 도로 패션영역",
        name = "닉네임",
        takers = 30,
        createAt = "1일 전",
    ),
    Maker(
        profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        title = "제 1회 도로 패션영역",
        name = "닉네임",
        takers = 30,
        createAt = "1일 전",
    ),
)

private const val HomeRecommendScreen = 0
private const val HomeFollowingScreen = 1

private val HomeIconSize = DpSize(24.dp)

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalLifecycleComposeApi::class,
)
@Composable
fun DuckieHomeScreen(
    vm: HomeViewModel,
) {
    val state = vm.state.collectAsStateWithLifecycle().value

    val homeTextTabTitles = persistentListOf(
        stringResource(id = R.string.recommend),
        stringResource(id = R.string.following),
    )

    val nestedScroll = rememberNestedScrollInteropConnection()

    Column(
        modifier = Modifier
            .padding(systemBarPaddings)
            .nestedScroll(nestedScroll),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeTopAppBar(
            titles = homeTextTabTitles,
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = {
                vm.changedSelectedTab(it)
            },
            onClickedEdit = {
                // TODO
            },
        )

        Crossfade(
            targetState = state.selectedTabIndex,
        ) { page ->
            when (page) {
                HomeRecommendScreen -> {
                    HomeRecommendScreen(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        homeRecommendItems = FakeHomeRecommendItem,
                        topicRecommendItems = FakeHomeTopicRecommendItem,
                    )
                }

                HomeFollowingScreen -> {
                    if (FakeFollowingTest.isEmpty()) {
                        HomeFollowingScreen(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            followingTest = FakeFollowingTest,
                        )
                    } else {
                        HomeFollowingInitialScreen(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            recommendCategories = FakeHomeInitRecommendCategories,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    titles: PersistentList<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onClickedEdit: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HomeTextTab(
            titles = titles,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
        )

        Spacer(modifier = Modifier.weight(1f))

        QuackImage(
            src = R.drawable.home_ic_create_24,
            onClick = onClickedEdit,
            size = HomeIconSize,
        )
    }
}
