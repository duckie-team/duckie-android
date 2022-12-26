/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.screen.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.viewmodel.sideeffect.HomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.viewmodel.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

private val DummyJumbotrons =
    (0..2).map { index ->
        HomeState.HomeRecommendJumbotron(
            image = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
            title = "제 ${index}회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        )
    }.toPersistentList()

private val DummyRecommendTopics =
    (0..10).map {
        HomeState.TopicRecommendItem(
            title = "쿠키좀 쿠워봤어?\n#웹툰 퀴즈",
            tag = "#웹툰",
            items = persistentListOf(
                HomeState.TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "외모지상주의 잘 알아?",
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                HomeState.TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                HomeState.TopicRecommendItem.DuckTest(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    examineeNumber = 20,
                    recommendId = 1,
                )
            )
        )
    }.toPersistentList()

private val DummyRecommendFollower = (0..3).map { index ->
    HomeState.RecommendUserByTopic(
        topic = "연예인$index",
        users = (0..5).map {
            HomeState.RecommendUser(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "닉네임",
                examineeNumber = 20,
                createAt = "1일 전",
                userId = 0,
            )
        }.toPersistentList()
    )
}.toPersistentList()

private val DummyRecommendFollowerTest = (0..5).map {
    HomeState.TestMaker(
        title = "제 1회 도로 패션영역",
        examineeNumber = 30,
        createAt = "1일 전",
        coverUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
        owner = HomeState.TestMaker.User(
            profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
            name = "닉네임",
        ),
    )
}.toPersistentList()

@Singleton
class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    // TODO(limsaehyun): Request Server
    fun fetchRecommendations() {
        updateState { prevState ->
            prevState.copy(
                jumbotrons = DummyJumbotrons,
                recommendTopics = DummyRecommendTopics,
            )
        }
    }

    // TODO(limsaehyun): Request Server
    fun fetchRecommendFollowing() {
        updateState { prevState ->
            prevState.copy(
                recommendFollowing = DummyRecommendFollower,
            )
        }
    }

    // TODO(limsaehyun): Request Server
    fun fetchRecommendFollowingTest() {
        updateState { prevState ->
            prevState.copy(
                recommendFollowingTest = DummyRecommendFollowerTest,
            )
        }
    }

    fun navigationPage(
        step: BottomNavigationStep,
    ) {
        updateState { state ->
            state.copy(
                step = step,
            )
        }
    }

    fun changedSelectedTab(
        step: HomeStep,
    ) {
        updateState { state ->
            state.copy(
                selectedTabIndex = step,
            )
        }
    }
}
