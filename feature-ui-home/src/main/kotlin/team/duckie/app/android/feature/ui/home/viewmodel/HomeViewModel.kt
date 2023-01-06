/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MaxLineLength") // TODO(limsaehyun): 더미데이터를 위해 임시로 구현, 추후에 제거 필요

package team.duckie.app.android.feature.ui.home.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import team.duckie.app.android.domain.recommendation.usecase.FetchFollowingTestUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchJumbotronsUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendFollowingUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchRecommendationsUseCase
import team.duckie.app.android.feature.ui.home.constants.HomeStep
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.ui.home.viewmodel.sideeffect.HomeSideEffect
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.viewmodel.BaseViewModel
import javax.inject.Inject

private val DummyJumbotrons =
    (0..2).map { index ->
        HomeState.HomeRecommendJumbotron(
            coverUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
            title = "제 ${index}회 도로 패션영역",
            content = "아 저 근데 너무 재미있을 것 같아요\n내 시험 최고",
            buttonContent = "하기싫음 하지마세요",
        )
    }.toPersistentList()

private val DummyRecommendUsers = (0..3).map { index ->
    HomeState.RecommendUserByTopic(
        topic = "연예인",
        users = (0..5).map {
            HomeState.RecommendUserByTopic.User(
                profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
                name = "user$it",
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

/**
 * fetchRecommendatiins 에서 사용되는 paging 단위
 */
private const val ITEMS_PER_PAGE = 10

@Immutable
class HomeViewModel @Inject constructor(
    private val fetchRecommendationsUseCase: FetchRecommendationsUseCase,
    private val fetchJumbotronsUseCase: FetchJumbotronsUseCase,
    private val fetchFollowingTestUseCase: FetchFollowingTestUseCase,
    private val fetchRecommendFollowingUseCase: FetchRecommendFollowingUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    // TODO(limsaehyun: Request Server
    suspend fun fetchJumbotrons() {
        fetchJumbotronsUseCase()
            .onSuccess {
                updateState { prevState ->
                    prevState.copy(
                        jumbotrons = DummyJumbotrons,
                    )
                }
            }.onFailure { exception ->
                postSideEffect {
                    HomeSideEffect.ReportError(exception)
                }
            }
    }

    fun fetchRecommendations() =
        Pager(
            pagingSourceFactory = {
                fetchRecommendationsUseCase()
            },
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
            ),
        ).flow

    // TODO(limsaehyun): Request Server
    suspend fun fetchRecommendFollowingTest() {
        updateState { prevState ->
            prevState.copy(
                isHomeLoading = true,
            )
        }
        delay(2.seconds)
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
                        isHomeLoading = false,
                    )
                }
            }
    }

    // TODO(limsaehyun): Request Server
    suspend fun fetchRecommendFollowing() {
        updateState { prevState ->
            prevState.copy(
                isHomeLoading = true,
            )
        }
        delay(2.seconds)
        fetchRecommendFollowingUseCase()
            .onSuccess {
                updateState { prevState ->
                    prevState.copy(
                        recommendFollowing = DummyRecommendUsers,
                    )
                }
            }.onFailure { exception ->
                postSideEffect {
                    HomeSideEffect.ReportError(exception)
                }
            }.also {
                updateState { prevState ->
                    prevState.copy(
                        isHomeLoading = false,
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
}
