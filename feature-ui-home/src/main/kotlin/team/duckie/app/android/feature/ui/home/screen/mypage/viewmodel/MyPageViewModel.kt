/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */


package team.duckie.app.android.feature.ui.home.screen.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.user.usecase.FetchUserProfileUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.ui.home.screen.mypage.viewmodel.sideeffect.MyPageSideEffect
import team.duckie.app.android.feature.ui.home.screen.mypage.viewmodel.state.MyPageState
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
    private val getMeUseCase: GetMeUseCase,
) : ContainerHost<MyPageState, MyPageSideEffect>, ViewModel() {

    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    fun getUserProfile() = intent {
        updateLoading(true)
        val job = viewModelScope.launch {
            getMeUseCase()
                .onSuccess { me ->
                    reduce { state.copy(me = me) }
                }.onFailure {
                    postSideEffect(MyPageSideEffect.ReportError(it))
                }
        }.apply { join() }
        if (job.isCancelled.not()) {
            state.me?.let {
                viewModelScope.launch {
                    fetchUserProfileUseCase(it.id).onSuccess { profile ->
                        reduce {
                            state.copy(
                                userProfile = profile
                            )
                        }
                    }.onFailure {
                        postSideEffect(MyPageSideEffect.ReportError(it))
                    }
                }
            }
        }
        updateLoading(false)
    }

    private fun updateLoading(
        loading: Boolean,
    ) = intent {
        reduce {
            state.copy(isLoading = loading)
        }
    }

    fun clickNotification() = intent {
        postSideEffect(MyPageSideEffect.NavigateToNotification)
    }

    fun clickSetting() = intent {
        postSideEffect(MyPageSideEffect.NavigateToSetting)
    }

    fun clickEditProfile(message: String) = intent {
        postSideEffect(MyPageSideEffect.SendToast(message))
    }

    fun clickEditTag(message: String) = intent {
        postSideEffect(MyPageSideEffect.SendToast(message))
    }

    fun clickExam(exam: DuckTestCoverItem) = intent {
        postSideEffect(MyPageSideEffect.NavigateToExamDetail(exam.testId))
    }

    fun clickMakeExam() = intent {
        postSideEffect(MyPageSideEffect.NavigateToMakeExam)
    }

    fun clickFavoriteTag(message: String) = intent {
        postSideEffect(MyPageSideEffect.SendToast(message))
    }
}
