/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

// TODO(limsaehyun): 더미 데이터 때문에 MaxLineHeight 발생 추후에 제거 필요
@file:Suppress("MaxLineLength")

package team.duckie.app.android.feature.ui.home.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import team.duckie.app.android.feature.ui.home.component.TextTabLayout
import team.duckie.app.android.feature.ui.home.viewmodel.HomeViewModel
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.util.DpSize

private val FakeHomeRecommendItem =
    persistentListOf(
        HomeRecommendItem(
            image = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
            title = "제 1회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        ),
        HomeRecommendItem(
            image = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
            title = "제 2회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        ),
        HomeRecommendItem(
            image = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
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
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    examineeNumber = 20,
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
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    examineeNumber = 20,
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
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 0,
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 1,
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 2,
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 3,
            ),
        ),
    ),
    RecommendCategories(
        topic = "영화",
        users = persistentListOf(
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 3,
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 3,
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 3,
            ),
            RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 3,
            ),
        ),
    )
)

private val FakeFollowingTest = persistentListOf(
    Maker(
        title = "제 1회 도로 패션영역",
        examineeNumber = 30,
        createAt = "1일 전",
        coverUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
        owner = Maker.User(
            profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
            name = "닉네임",
        )
    ),
    Maker(
        title = "제 1회 도로 패션영역",
        examineeNumber = 30,
        createAt = "1일 전",
        coverUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
        owner = Maker.User(
            profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
            name = "닉네임",
        )
    ),
    Maker(
        title = "제 1회 도로 패션영역",
        examineeNumber = 30,
        createAt = "1일 전",
        coverUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
        owner = Maker.User(
            profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
            name = "닉네임",
        )
    ),
)

enum class HomeStep(
    val index: Int,
){
    HomeRecommendScreen(
        index = 0,
    ),

    HomeFollowingScreen(
        index = 1,
    );

    companion object {
        fun toStep(value: Int) = HomeStep.values().first { it.index == value }
    }
}

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
            .nestedScroll(nestedScroll),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeTopAppBar(
            titles = homeTextTabTitles,
            selectedTabIndex = state.selectedTabIndex.index,
            onTabSelected = { step ->
                vm.changedSelectedTab(
                    HomeStep.toStep(step)
                )
            },
            onClickedEdit = {
                // TODO
            },
        )

        Crossfade(
            targetState = state.selectedTabIndex,
        ) { page ->
            when (page) {
                HomeStep.HomeRecommendScreen -> {
                    HomeRecommendScreen(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 12.dp,
                        ),
                        homeRecommendItems = FakeHomeRecommendItem,
                        topicRecommendItems = FakeHomeTopicRecommendItem,
                    )
                }

                HomeStep.HomeFollowingScreen -> {
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
        TextTabLayout(
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
