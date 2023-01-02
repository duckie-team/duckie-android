/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MaxLineLength") // TODO(limsaehyun): 더미 데이터를 위해 임시로 구현, 제거 필요

package team.duckie.app.android.feature.ui.search.viewmodel

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.recommendation.usecase.FetchSearchResultUseCase
import team.duckie.app.android.feature.ui.search.constans.SearchResultStep
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchResultSideEffect
import team.duckie.app.android.feature.ui.search.viewmodel.state.SearchResultState
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.app.android.util.kotlin.seconds
import team.duckie.app.android.util.viewmodel.BaseViewModel
import javax.inject.Inject

private val DummySearchResultForExam = (0..10).map {
    DuckTestCoverItem(
        testId = it,
        coverImg = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
        nickname = "user$it",
        title = "test$it",
        examineeNumber = it,
    )
}.toPersistentList()

private val DummyResultResultForUser = (0..20).map {
    SearchResultState.User(
        userId = it,
        profile = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        name = "user$it",
        examineeNumber = it,
        createAt = "1일 전",
        isFollowing = it % 2 == 0,
    )
}.toPersistentList()

@Immutable
class SearchResultViewModel @Inject constructor(
    private val fetchSearchResultUseCase: FetchSearchResultUseCase,
) : BaseViewModel<SearchResultState, SearchResultSideEffect>(SearchResultState()) {

    // TODO(limsaehyun): Request Server
    suspend fun fetchSearchResultForExam(tag: String) {
        updateState { prevState ->
            prevState.copy(
                isSearchResultLoading = true,
            )
        }
        delay(2.seconds)
        fetchSearchResultUseCase(
            tag = tag,
            type = SearchType.EXAM,
        ).onSuccess {
            updateState { prevState ->
                prevState.copy(
                    searchResultForTest = DummySearchResultForExam,
                )
            }
        }.onFailure { exception ->
            postSideEffect {
                SearchResultSideEffect.ReportError(exception)
            }
        }.also {
            updateState { prevState ->
                prevState.copy(
                    isSearchResultLoading = false,
                )
            }
        }
    }

    // TODO(limsaehyun): Request Server
    suspend fun fetchSearchResultForUser(tag: String) {
        updateState { prevState ->
            prevState.copy(
                isSearchResultLoading = true,
            )
        }
        delay(2.seconds)
        fetchSearchResultUseCase(
            tag = tag,
            type = SearchType.USER,
        ).onSuccess {
            updateState { prevState ->
                prevState.copy(
                    searchResultForUser = DummyResultResultForUser,
                )
            }
        }.onFailure { exception ->
            postSideEffect {
                SearchResultSideEffect.ReportError(exception)
            }
        }.also {
            updateState { prevState ->
                prevState.copy(
                    isSearchResultLoading = false,
                )
            }
        }
    }

    fun changeSearchResultTab(
        step: SearchResultStep,
    ) {
        updateState { prevState ->
            prevState.copy(
                tagSelectedTab = step,
            )
        }
    }

    fun changeSearchTag(
        tag: String,
    ) {
        updateState { prevState ->
            prevState.copy(
                searchTag = tag,
            )
        }
    }
}
