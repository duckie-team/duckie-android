/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.mypage

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
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.kotlin.exception.DuckieClientLogicProblemException
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.exam.usecase.GetHeartExamUseCase
import team.duckie.app.android.domain.exam.usecase.GetSubmittedExamUseCase
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.domain.examInstance.usecase.GetSolvedExamInstanceUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserProfileUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.profile.viewmodel.intent.MyPageIntent
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileStep
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val getHeartExamUseCase: GetHeartExamUseCase,
    private val getSubmittedExamUseCase: GetSubmittedExamUseCase,
    private val getSolvedExamInstance: GetSolvedExamInstanceUseCase,
) : ContainerHost<MyPageState, MyPageSideEffect>, ViewModel(), MyPageIntent {

    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    private val _heartExams = MutableStateFlow<PagingData<ProfileExam>>(PagingData.empty())
    val heartExams: Flow<PagingData<ProfileExam>> = _heartExams

    private val _submittedExams = MutableStateFlow<PagingData<ProfileExam>>(PagingData.empty())
    val submittedExams: Flow<PagingData<ProfileExam>> = _submittedExams

    private val _solvedExams = MutableStateFlow<PagingData<ProfileExamInstance>>(PagingData.empty())
    val solvedExams: Flow<PagingData<ProfileExamInstance>> = _solvedExams

    fun getUserProfile() = intent {
        startLoading()

        val job = viewModelScope.launch {
            getMeUseCase()
                .onSuccess { me ->
                    reduce { state.copy(me = me) }
                }.onFailure {
                    reduce { state.copy(step = ProfileStep.Error) }
                    postSideEffect(MyPageSideEffect.ReportError(it))
                }
        }.apply { join() }
        if (job.isCancelled.not()) {
            state.me?.let {
                viewModelScope.launch {
                    fetchUserProfileUseCase(it.id).onSuccess { profile ->
                        reduce {
                            state.copy(
                                isLoading = false,
                                userProfile = profile,
                            )
                        }
                    }.onFailure {
                        reduce { state.copy(step = ProfileStep.Error) }
                        postSideEffect(MyPageSideEffect.ReportError(it))
                    }
                }
            }
        }
    }

    fun onClickTag(tag: String) = intent {
        postSideEffect(MyPageSideEffect.NavigateToSearch(tag))
    }

    private fun startLoading() = intent {
        reduce {
            state.copy(isLoading = true)
        }
    }

    fun clickExam(exam: DuckTestCoverItem) = intent {
        postSideEffect(MyPageSideEffect.NavigateToExamDetail(exam.testId))
    }

    fun clickViewAll(viewAll: ProfileStep.ViewAll) = intent {
        if(state.isLoading) return@intent
        when (viewAll.examType) {
            ExamType.Heart -> fetchHeartExams()
            ExamType.Created -> fetchSubmittedExams()
            ExamType.Solved -> fetchSolvedExams()
        }
        reduce {
            state.copy(step = viewAll)
        }
    }

    fun clickViewAllBackPress() = intent {
        reduce {
            state.copy(step = ProfileStep.Profile)
        }
    }

    override fun clickNotification() = intent {
        postSideEffect(MyPageSideEffect.NavigateToNotification)
    }

    override fun clickSetting() = intent {
        postSideEffect(MyPageSideEffect.NavigateToSetting)
    }

    override fun clickEditProfile() = intent {
        postSideEffect(
            MyPageSideEffect.NavigateToEditProfile(
                userId = state.userProfile.user?.id
                    ?: throw DuckieClientLogicProblemException(code = "User is null"),
            ),
        )
    }

    override fun clickEditTag() = intent {
        postSideEffect(
            MyPageSideEffect.NavigateToTagEdit(
                userId = state.userProfile.user?.id
                    ?: throw DuckieClientLogicProblemException(code = "User is null"),
            ),
        )
    }

    override fun clickMakeExam() = intent {
        postSideEffect(MyPageSideEffect.NavigateToMakeExam)
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
                state.copy(
                    step = ProfileStep.Error,
                )
            }
            postSideEffect(MyPageSideEffect.ReportError(exception))
        }
    }

    private fun fetchHeartExams() {
        val state = container.stateFlow.value
        viewModelScope.launch {
            getHeartExamUseCase(state.me?.id ?: 0)
                .cachedIn(viewModelScope)
                .collect {
                    _heartExams.value = it
                }
        }
    }

    private fun fetchSubmittedExams() {
        val state = container.stateFlow.value
        viewModelScope.launch {
            getSubmittedExamUseCase(state.me?.id ?: 0)
                .cachedIn(viewModelScope)
                .collect {
                    _submittedExams.value = it
                }
        }
    }

    private fun fetchSolvedExams() {
        val state = container.stateFlow.value
        viewModelScope.launch {
            getSolvedExamInstance(state.me?.id ?: 0)
                .cachedIn(viewModelScope)
                .collect {
                    _solvedExams.value = it
                }
        }
    }
}
