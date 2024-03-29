/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.kotlin.exception.DuckieClientLogicProblemException
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
) : ContainerHost<MyPageState, MyPageSideEffect>, ViewModel(), MyPageIntent {

    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

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

    fun clickViewAll(
        examType: ExamType,
    ) = intent {
        postSideEffect(
            MyPageSideEffect.NavigateToViewAll(
                userId = state.me?.id ?: 0,
                examType = examType,
            ),
        )
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
}
