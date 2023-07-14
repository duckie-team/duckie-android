/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(FlowPreview::class)

package team.duckie.app.android.feature.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.ui.const.Debounce
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.dialog.DuckieSelectableType
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.exam.usecase.GetHeartExamUseCase
import team.duckie.app.android.domain.exam.usecase.GetSubmittedExamUseCase
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.domain.examInstance.usecase.GetSolvedExamInstance
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.ignore.usecase.UserIgnoreUseCase
import team.duckie.app.android.domain.report.usecase.ReportUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserProfileUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.profile.viewmodel.intent.MyPageIntent
import team.duckie.app.android.feature.profile.viewmodel.intent.OtherPageIntent
import team.duckie.app.android.feature.profile.viewmodel.sideeffect.ProfileSideEffect
import team.duckie.app.android.feature.profile.viewmodel.state.ExamType
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileState
import team.duckie.app.android.feature.profile.viewmodel.state.ProfileStep
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val reportUseCase: ReportUseCase,
    private val followUseCase: FollowUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val ignoreUseCase: UserIgnoreUseCase,
    private val getHeartExamUseCase: GetHeartExamUseCase,
    private val getSubmittedExamUseCase: GetSubmittedExamUseCase,
    private val getSolvedExamInstance: GetSolvedExamInstance,
) : ContainerHost<ProfileState, ProfileSideEffect>, ViewModel(), MyPageIntent, OtherPageIntent {

    override val container = container<ProfileState, ProfileSideEffect>(ProfileState())

    private val _heartExams = MutableStateFlow<PagingData<ProfileExam>>(PagingData.empty())
    val heartExams: Flow<PagingData<ProfileExam>> = _heartExams

    private val _submittedExams = MutableStateFlow<PagingData<ProfileExam>>(PagingData.empty())
    val submittedExams: Flow<PagingData<ProfileExam>> = _submittedExams

    private val _solvedExams = MutableStateFlow<PagingData<ProfileExamInstance>>(PagingData.empty())
    val solvedExams: Flow<PagingData<ProfileExamInstance>> = _solvedExams

    private val followEvent = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply {
        intent {
            debounce(Debounce.ClickDebounce).collectLatest {
                val state = container.stateFlow.value
                followUseCase(
                    FollowBody(state.userId),
                    state.follow,
                ).onSuccess { apiResult ->
                    if (apiResult) {
                        reduce {
                            state.copy(follow = state.follow.not())
                        }
                    }
                }.onFailure {
                    intent { postSideEffect(ProfileSideEffect.ReportError(it)) }
                }
                getUserProfile()
            }
        }
    }

    init {
        init()
    }

    fun init() = intent {
        val userId = savedStateHandle.getStateFlow(Extras.UserId, 0).value
        startLoading()

        val job = viewModelScope.launch {
            getMeUseCase()
                .onSuccess { me ->
                    reduce { state.copy(me = me) }
                }.onFailure {
                    reduce { state.copy(step = ProfileStep.Error) }
                    postSideEffect(ProfileSideEffect.ReportError(it))
                }.also {
                    stopLoading()
                }
        }.apply { join() }

        if (job.isCancelled.not()) {
            state.me?.let {
                viewModelScope.launch {
                    fetchUserProfileUseCase(userId).onSuccess { profile ->
                        reduce {
                            state.copy(
                                step = ProfileStep.Profile,
                                userProfile = profile,
                                isMe = it.id == userId,
                                follow = profile.user?.follow == null,
                                userId = userId,
                            )
                        }
                    }.onFailure {
                        reduce { state.copy(step = ProfileStep.Error) }
                        postSideEffect(ProfileSideEffect.ReportError(it))
                    }.also {
                        stopLoading()
                    }
                }
            }
        }
    }

    fun updateBottomSheetDialogType(type: DuckieSelectableType) = intent {
        reduce {
            state.copy(bottomSheetDialogType = persistentListOf(type))
        }
    }

    fun ignore(targetId: Int) = intent {
        ignoreUseCase(targetId)
            .onSuccess {
                postSideEffect(
                    ProfileSideEffect.NavigateToBack(
                        isFollow = state.follow,
                        userId = state.userId,
                    ),
                )
            }
            .onFailure { exception ->
                postSideEffect(ProfileSideEffect.ReportError(exception))
            }
            .also {
                updateIgnoreDialogVisible(false)
            }
    }

    fun updateIgnoreDialogVisible(visible: Boolean) = intent {
        reduce {
            state.copy(ignoreDialogVisible = visible)
        }
    }

    fun getUserProfile() = intent {
        startLoading()
        viewModelScope.launch {
            fetchUserProfileUseCase(state.userId)
                .onSuccess { profile ->
                    reduce {
                        state.copy(
                            userProfile = profile,
                            isMe = state.isMe,
                        )
                    }
                }
                .onFailure {
                    reduce { state.copy(step = ProfileStep.Error) }
                    postSideEffect(ProfileSideEffect.ReportError(it))
                }.also {
                    stopLoading()
                }
        }
    }

    fun clickViewAll(viewAll: ProfileStep.ViewAll) = intent {
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

    fun report() = intent {
        reportUseCase(state.reportExamId)
            .onSuccess {
                updateReportDialogVisible(true)
            }
            .onFailure { exception ->
                postSideEffect(ProfileSideEffect.ReportError(exception))
            }
    }

    fun updateReportDialogVisible(visible: Boolean) = intent {
        reduce {
            state.copy(reportDialogVisible = visible)
        }
    }

    fun onClickTag(tag: String) = intent {
        postSideEffect(ProfileSideEffect.NavigateToSearch(tag))
    }

    fun navigateFriends(friendType: FriendsType, userId: Int, nickname: String) = intent {
        postSideEffect(ProfileSideEffect.NavigateToFriends(friendType, userId, nickname))
    }

    override fun clickBackPress() = intent {
        postSideEffect(
            ProfileSideEffect.NavigateToBack(
                isFollow = state.follow,
                userId = state.userId,
            ),
        )
    }

    override fun clickFollow() = intent {
        followEvent.emit(Unit)
    }

    fun clickExam(exam: DuckTestCoverItem) = intent {
        postSideEffect(ProfileSideEffect.NavigateToExamDetail(exam.testId))
    }

    override fun clickNotification() = intent {
        postSideEffect(ProfileSideEffect.NavigateToNotification)
    }

    override fun clickSetting() = intent {
        postSideEffect(ProfileSideEffect.NavigateToSetting)
    }

    override fun clickEditProfile() = intent {
        postSideEffect(ProfileSideEffect.NavigateToEditProfile(state.userId))
    }

    override fun clickEditTag() = intent {
        postSideEffect(ProfileSideEffect.NavigateToTagEdit(state.userId))
    }

    override fun clickMakeExam() = intent {
        postSideEffect(ProfileSideEffect.NavigateToMakeExam)
    }

    private fun startLoading() = intent {
        reduce {
            state.copy(isLoading = true)
        }
    }

    private fun stopLoading() = intent {
        reduce {
            state.copy(isLoading = false)
        }
    }

    private fun fetchHeartExams() {
        val state = container.stateFlow.value
        viewModelScope.launch {
            getHeartExamUseCase(state.userId)
                .cachedIn(viewModelScope)
                .collect {
                    _heartExams.value = it
                }
        }
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
            postSideEffect(ProfileSideEffect.ReportError(exception))
        }
    }

    private fun fetchSubmittedExams() {
        val state = container.stateFlow.value
        viewModelScope.launch {
            getSubmittedExamUseCase(state.userId)
                .cachedIn(viewModelScope)
                .collect {
                    _submittedExams.value = it
                }
        }
    }

    private fun fetchSolvedExams() {
        val state = container.stateFlow.value
        viewModelScope.launch {
            getSolvedExamInstance(state.userId)
                .cachedIn(viewModelScope)
                .collect {
                    _solvedExams.value = it
                }
        }
    }
}
