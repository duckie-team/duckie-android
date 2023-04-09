/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe
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

    fun getUserProfile() = intent {
        val userId = savedStateHandle.getStateFlow(Extras.userId, 0).value
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
                                follow = profile.user?.follow == null,
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

    fun report() = intent {
        reportUseCase(state.reportExamId)
            .onSuccess {
                updateReportDialogVisible(true)
            }
            .onFailure { exception ->
                postSideEffect(ProfileSideEffect.ReportError(exception))
            }
        getUserProfile()
    }

    fun followUser() = viewModelScope.launch {
        val state = container.stateFlow.value

        followUseCase(
            FollowBody(
                state.userProfile.user?.id ?: duckieResponseFieldNpe("팔로우할 유저는 반드시 있어야 합니다."),
            ),
            state.follow.not(),
        ).onSuccess { apiResult ->
            if (apiResult) {
                intent {
                    reduce {
                        state.copy(follow = state.follow.not())
                    }
                }
            }
        }.onFailure {
            intent { postSideEffect(ProfileSideEffect.ReportError(it)) }
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

    override fun clickFollow() {
        TODO("Not yet implemented")
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
