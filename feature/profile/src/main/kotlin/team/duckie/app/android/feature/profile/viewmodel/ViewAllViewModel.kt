/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.savedstate.getOrThrow
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.exam.usecase.GetContinueMusicExamUseCase
import team.duckie.app.android.domain.exam.usecase.GetHeartExamUseCase
import team.duckie.app.android.domain.exam.usecase.GetSubmittedExamUseCase
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.domain.examInstance.usecase.GetSolvedExamInstanceUseCase
import team.duckie.app.android.feature.profile.viewmodel.sideeffect.ViewAllSideEffect
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
import team.duckie.app.android.feature.profile.viewmodel.state.ViewAllState
import javax.inject.Inject

@HiltViewModel
internal class ViewAllViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getHeartExamUseCase: GetHeartExamUseCase,
    private val getSubmittedExamUseCase: GetSubmittedExamUseCase,
    private val getSolvedExamInstance: GetSolvedExamInstanceUseCase,
    private val getContinueMusicExamUseCase: GetContinueMusicExamUseCase,
) : ContainerHost<ViewAllState, ViewAllSideEffect>, ViewModel() {
    override val container: Container<ViewAllState, ViewAllSideEffect> =
        container(ViewAllState.Loading)

    init {
        fetchExamItems()
    }

    private val _heartExams = MutableStateFlow<PagingData<ProfileExam>>(PagingData.empty())
    val heartExams: Flow<PagingData<ProfileExam>> = _heartExams

    private val _submittedExams = MutableStateFlow<PagingData<ProfileExam>>(PagingData.empty())
    val submittedExams: Flow<PagingData<ProfileExam>> = _submittedExams

    private val _solvedExams = MutableStateFlow<PagingData<ProfileExamInstance>>(PagingData.empty())
    val solvedExams: Flow<PagingData<ProfileExamInstance>> = _solvedExams

    private val _continueMusicExams = MutableStateFlow<PagingData<ProfileExam>>(PagingData.empty())
    val continueMusicExams: Flow<PagingData<ProfileExam>> = _continueMusicExams

    fun fetchExamItems() = intent {
        val userId = savedStateHandle.getOrThrow<Int>(Extras.UserId)
        val examType = savedStateHandle.getOrThrow<ExamType>(Extras.ExamType)
        reduce {
            ViewAllState.Success(
                userId = userId,
                examType = examType,
            )
        }
        when (examType) {
            ExamType.Heart -> fetchHeartExams()
            ExamType.Created -> fetchSubmittedExams()
            ExamType.Solved -> fetchSolvedExams()
            ExamType.Continue -> fetchContinueMusicExams()
        }
    }

    private fun fetchHeartExams() {
        val state = container.stateFlow.value as ViewAllState.Success
        viewModelScope.launch {
            getHeartExamUseCase(state.userId)
                .cachedIn(viewModelScope)
                .collect {
                    _heartExams.value = it
                }
        }
    }

    private fun fetchSubmittedExams() {
        val state = container.stateFlow.value as ViewAllState.Success
        viewModelScope.launch {
            getSubmittedExamUseCase(state.userId)
                .cachedIn(viewModelScope)
                .collect {
                    _submittedExams.value = it
                }
        }
    }

    private fun fetchSolvedExams() {
        val state = container.stateFlow.value as ViewAllState.Success
        viewModelScope.launch {
            getSolvedExamInstance(state.userId)
                .cachedIn(viewModelScope)
                .collect {
                    _solvedExams.value = it
                }
        }
    }

    private fun fetchContinueMusicExams() {
        val state = container.stateFlow.value as ViewAllState.Success
        viewModelScope.launch {
            getContinueMusicExamUseCase(state.userId)
                .cachedIn(viewModelScope)
                .collect {
                    _continueMusicExams.value = it
                }
        }
    }

    fun clickViewAllBackPress() = intent {
        postSideEffect(ViewAllSideEffect.NavigateToProfile)
    }

    fun clickExam(exam: DuckTestCoverItem) = intent {
        postSideEffect(ViewAllSideEffect.NavigateToExamDetail(exam.testId))
    }

    fun handleLoadState(loadStates: LoadStates) = intent {
        val errorLoadState = arrayOf(
            loadStates.append,
            loadStates.prepend,
            loadStates.refresh,
        ).filterIsInstance(LoadState.Error::class.java).firstOrNull()

        val exception = errorLoadState?.error

        if (exception != null) {
            reduce {
                ViewAllState.Error(exception)
            }
            postSideEffect(ViewAllSideEffect.ReportError(exception))
        }
    }
}
