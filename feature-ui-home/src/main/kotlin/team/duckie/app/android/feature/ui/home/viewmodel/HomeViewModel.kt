/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.domain.recommendation.usecase.FetchFollowingTestUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendTagUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendationsUseCase
import team.duckie.app.android.feature.ui.home.component.DuckTestCoverItem
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.screen.TagStep
import team.duckie.app.android.feature.ui.home.viewmodel.sideeffect.HomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.viewmodel.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

private val DummyJumbotrons =
    (0..2).map { index ->
        HomeState.HomeRecommendJumbotron(
            coverUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
            title = "제 ${index}회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        )
    }.toPersistentList()

private val DummyRecommendTopics =
    (0..10).map {
        HomeState.RecommendTopic(
            title = "쿠키좀 쿠워봤어?\n#웹툰 퀴즈",
            tag = "#웹툰",
            items = persistentListOf(
                HomeState.RecommendTopic.Test(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "외모지상주의 잘 알아?",
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                HomeState.RecommendTopic.Test(
                    coverImg = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    nickname = "세현",
                    title = "안 아프게 맞는법!",
                    examineeNumber = 20,
                    recommendId = 1,
                ),
                HomeState.RecommendTopic.Test(
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
            HomeState.RecommendUserByTopic.User(
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
    HomeState.FollowingTest(
        title = "제 1회 도로 패션영역",
        examineeNumber = 30,
        createAt = "1일 전",
        coverUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
        owner = HomeState.FollowingTest.User(
            profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
            name = "닉네임",
        ),
    )
}.toPersistentList()

private val DummyRecommendTag = (0..10).map {
    DuckTestCoverItem(
        testId = it,
        coverImg = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
        nickname = "user$it",
        title = "test$it",
        examineeNumber = it,
    )
}.toPersistentList()

@Singleton
class HomeViewModel @Inject constructor(
    private val fetchRecommendationsUseCase: FetchRecommendationsUseCase,
    private val fetchFollowingTestUseCase: FetchFollowingTestUseCase,
    private val fetchRecommendFollowingUseCase: FetchRecommendFollowingUseCase,
    private val fetchRecommendTagUseCase: FetchRecommendTagUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    // TODO(limsaehyun): Request Server
    suspend fun fetchRecommendations() {
        fetchRecommendationsUseCase()
            .onSuccess {
                updateState { prevState ->
                    prevState.copy(
                        jumbotrons = DummyJumbotrons,
                        recommendTopics = DummyRecommendTopics,
                    )
                }
            }.onFailure { exception ->
                postSideEffect {
                    HomeSideEffect.ReportError(exception)
                }
            }.also {
                updateState { prevState ->
                    prevState.copy(
                        isHomeRecommendLoading = false,
                    )
                }
            }
    }

    // TODO(limsaehyun): Request Server
    suspend fun fetchRecommendFollowingTest() {
        fetchFollowingTestUseCase()
            .onSuccess {
                updateState { prevState ->
                    prevState.copy(
                        recommendFollowingTest = DummyRecommendFollowerTest,
                    )
                }
            }.onFailure { exception ->
                postSideEffect {
                    HomeSideEffect.ReportError(exception)
                }
            }.also {
                updateState { prevState ->
                    prevState.copy(
                        isHomeRecommendLoading = false,
                    )
                }
            }
    }

    // TODO(limsaehyun): Request Server
    suspend fun fetchRecommendFollowing() {
        fetchRecommendFollowingUseCase()
            .onSuccess {
                updateState { prevState ->
                    prevState.copy(
                        recommendFollowing = DummyRecommendFollower,
                    )
                }
            }.onFailure { exception ->
                postSideEffect {
                    HomeSideEffect.ReportError(exception)
                }
            }.also {
                updateState { prevState ->
                    prevState.copy(
                        isHomeFollowingInitialLoading = false,
                    )
                }
            }
    }

    suspend fun fetchRecommendTag(tag: String) {
        fetchRecommendTagUseCase(
            tag = tag,
        ).onSuccess {
            updateState { prevState ->
                prevState.copy(
                    recommendByTags = DummyRecommendTag,
                )
            }
        }.onFailure { exception ->
            postSideEffect {
                HomeSideEffect.ReportError(exception)
            }
        }.also {
            updateState { prevState ->
                prevState.copy(
                    isHomeTagLoading = false,
                )
            }
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

    fun changedHomeScreen(
        step: HomeStep,
    ) {
        updateState { prevState ->
            prevState.copy(
                homeSelectedIndex = step,
            )
        }
    }

    fun changedTagSelectedTab(
        step: TagStep,
    ) {
        updateState { prevState ->
            prevState.copy(
                tagSelectedTabIndex = step,
            )
        }
    }

    fun selectedTag(
        tag: String,
    ) {
        updateState { prevState ->
            prevState.copy(
                selectedTag = tag
            )
        }
    }
}
