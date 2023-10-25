/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.usecase.FetchMusicJumbotronsUseCase
import team.duckie.app.android.domain.recommendation.usecase.FetchMusicRecommendationsUseCase
import team.duckie.app.android.feature.home.viewmodel.home.skeletonRecommendationItems
import javax.inject.Inject

@HiltViewModel
internal class MusicViewModel @Inject constructor(
    private val fetchMusicRecommendationsUseCase: FetchMusicRecommendationsUseCase,
    private val fetchMusicJumbotronsUseCase: FetchMusicJumbotronsUseCase,
) : ContainerHost<MusicState, MusicSideEffect>, ViewModel() {
    override val container = container<MusicState, MusicSideEffect>(MusicState())

    private val _musicRecommendations =
        MutableStateFlow(PagingData.from(skeletonRecommendationItems))
    val musicRecommendations: Flow<PagingData<RecommendationItem>> = _musicRecommendations

    init {
        fetchMusicJumbotrons()
        fetchMusicRecommendations()
    }

    fun saveHeroModulePage(page: Int) = intent {
        reduce {
            state.copy(heroModulePage = page)
        }
    }

    fun clickRetryMusic() = intent {
        postSideEffect(MusicSideEffect.ListPullUp)
    }

    fun clickMusicExam(exam: Exam) = intent {
        postSideEffect(MusicSideEffect.NavigateToMusicExamDetail(exam.id))
    }

    fun clickShowAll(userId: Int) = intent {
        postSideEffect(MusicSideEffect.NavigateToViewAll(userId))
    }

    private fun fetchMusicJumbotrons() = intent {
        fetchMusicJumbotronsUseCase()
            .onSuccess {
                reduce {
                    state.copy(musicJumbotrons = it)
                }
            }.onFailure {
                postSideEffect(MusicSideEffect.ReportError(it))
            }
    }

    private fun fetchMusicRecommendations() {
        viewModelScope.launch {
            fetchMusicRecommendationsUseCase()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _musicRecommendations.value = pagingData
                }
        }
    }
}

