/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.search.usecase.SearchExamsUseCase
import team.duckie.app.android.domain.search.usecase.SearchUsersUseCase
import team.duckie.app.android.feature.ui.search.constants.SearchResultStep
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchResultSideEffect
import team.duckie.app.android.feature.ui.search.viewmodel.state.SearchResultState
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.app.android.util.kotlin.seconds

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
        profileImgUrl = "https://www.pngitem.com/pimgs/m/80-800194_transparent-users-icon-png-flat-user-icon-png.png",
        nickname = "user$it",
        favoriteTag = "도로패션",
        tier = "덕력 20%",
        isFollowing = it % 2 == 0,
    )
}.toPersistentList()

@HiltViewModel
internal class SearchResultViewModel @Inject constructor(
    private val searchExamsUseCase: SearchExamsUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
) : ContainerHost<SearchResultState, SearchResultSideEffect>, ViewModel() {

    override val container = container<SearchResultState, SearchResultSideEffect>(SearchResultState())

    // TODO(limsaehyun): Request Server
    fun fetchSearchResultForExam(tag: String) = intent {
        updateSearchResultLoading(true)
        delay(2.seconds)
        searchExamsUseCase(
            tag = tag,
        ).onSuccess {
            reduce {
                state.copy(
                    searchResultForTest = DummySearchResultForExam,
                )
            }
        }.onFailure { exception ->
            postSideEffect(SearchResultSideEffect.ReportError(exception))
        }.also {
            updateSearchResultLoading(false)
        }
    }

    // TODO(limsaehyun): Request Server
    fun fetchSearchResultForUser(tag: String) = intent {
        updateSearchResultLoading(true)
        searchUsersUseCase(
            tag = tag,
        ).onSuccess {
            reduce {
                state.copy(
                    searchResultForUser = DummyResultResultForUser,
                )
            }
        }.onFailure { exception ->
            postSideEffect(SearchResultSideEffect.ReportError(exception))
        }.also {
            updateSearchResultLoading(false)
        }
    }

    private fun updateSearchResultLoading(loading: Boolean) = intent {
        reduce {
            state.copy(
                isSearchResultLoading = loading,
            )
        }
    }

    fun changeSearchResultTab(
        step: SearchResultStep,
    ) = intent {
        reduce {
            state.copy(
                tagSelectedTab = step,
            )
        }
    }

    fun changeSearchTag(
        tag: String,
    ) = intent {
        reduce {
            state.copy(
                searchTag = tag,
            )
        }
    }
}
