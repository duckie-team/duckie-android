/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(FlowPreview::class)

package team.duckie.app.android.feature.ui.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.usecase.FollowUseCase
import team.duckie.app.android.domain.report.usecase.ReportUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserProfileUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.profile.viewmodel.intent.MyPageIntent
import team.duckie.app.android.feature.ui.profile.viewmodel.intent.OtherPageIntent
import team.duckie.app.android.feature.ui.profile.viewmodel.sideeffect.ProfileSideEffect
import team.duckie.app.android.feature.ui.profile.viewmodel.state.ProfileState
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.app.android.util.ui.const.Debounce
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val reportUseCase: ReportUseCase,
    private val followUseCase: FollowUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<ProfileState, ProfileSideEffect>, ViewModel(), MyPageIntent, OtherPageIntent {

    override val container = container<ProfileState, ProfileSideEffect>(ProfileState())

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

    fun init() = intent {
        val userId = savedStateHandle.getStateFlow(Extras.UserId, 0).value
        updateLoading(true)
        val job = viewModelScope.launch {
            getMeUseCase()
                .onSuccess { me ->
                    reduce { state.copy(me = me) }
                }.onFailure {
                    postSideEffect(ProfileSideEffect.ReportError(it))
                }
        }.apply { join() }
        if (job.isCancelled.not()) {
            state.me?.let {
                viewModelScope.launch {
                    fetchUserProfileUseCase(userId).onSuccess { profile ->
                        reduce {
                            state.copy(
                                userProfile = profile,
                                isMe = it.id == userId,
                                follow = profile.user?.follow == null, // following하지 않을 때 null
                                userId = userId,
                            )
                        }
                    }.onFailure {
                        postSideEffect(ProfileSideEffect.ReportError(it))
                    }
                }
            }
        }
        updateLoading(false)
    }

    private fun getUserProfile() = intent {
        viewModelScope.launch {
            fetchUserProfileUseCase(state.userId).onSuccess { profile ->
                reduce {
                    state.copy(
                        userProfile = profile,
                        isMe = state.isMe,
                    )
                }
            }.onFailure {
                postSideEffect(ProfileSideEffect.ReportError(it))
            }
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

    override fun clickBackPress() = intent {
        postSideEffect(ProfileSideEffect.NavigateToBack)
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

    override fun clickEditProfile(message: String) = intent {
        postSideEffect(ProfileSideEffect.SendToast(message))
    }

    override fun clickEditTag(message: String) = intent {
        postSideEffect(ProfileSideEffect.SendToast(message))
    }

    override fun clickMakeExam() = intent {
        postSideEffect(ProfileSideEffect.NavigateToMakeExam)
    }

    private fun updateLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(isLoading = loading)
        }
    }
}
