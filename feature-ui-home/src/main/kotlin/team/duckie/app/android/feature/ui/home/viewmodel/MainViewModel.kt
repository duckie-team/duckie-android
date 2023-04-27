/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.report.usecase.ReportUseCase
import team.duckie.app.android.domain.tag.usecase.FetchPopularTagsUseCase
import team.duckie.app.android.feature.ui.home.constants.BottomNavigationStep
import team.duckie.app.android.shared.ui.compose.dialog.ReportAlreadyExists
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.app.android.util.kotlin.exception.isReportAlreadyExists
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val fetchPopularTagsUseCase: FetchPopularTagsUseCase,
    private val reportUseCase: ReportUseCase,
) : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState())

    /**
     * 신고할 게시물의 [examId] 를 업데이트합니다.
     */
    fun setReportExamId(examId: Int) = intent {
        reduce {
            state.copy(reportExamId = examId)
        }
    }

    /**
     * [state.reportExamId]에 해당하는 게시물을 신고합니다.
     */
    fun report() = intent {
        reportUseCase(state.reportExamId)
            .onSuccess {
                updateReportDialogVisible(true)
            }
            .onFailure { exception ->
                when {
                    exception.isReportAlreadyExists -> postSideEffect(
                        MainSideEffect.SendToast(ReportAlreadyExists),
                    )

                    else -> postSideEffect(MainSideEffect.ReportError(exception))
                }
            }
    }

    fun updateReportDialogVisible(visible: Boolean) = intent {
        reduce {
            state.copy(reportDialogVisible = visible)
        }
    }

    /**
     * 인기 태그 목록을 가져온다
     * TODO(limsaehyun): 추후 검색 로직이 복잡해지면 분리해야 함
     * */
    fun fetchPopularTags() = intent {
        fetchPopularTagsUseCase()
            .onSuccess { tags ->
                reduce {
                    state.copy(
                        popularTags = tags,
                    )
                }
            }
            .onFailure { exception ->
                postSideEffect(MainSideEffect.ReportError(exception))
            }
    }

    /** 홈 화면의 BottomNavigation 상태를 [step]으로 바꾼다 */
    fun navigationPage(
        step: BottomNavigationStep,
    ) = intent {
        if (step == BottomNavigationStep.RankingScreen && state.bottomNavigationStep == BottomNavigationStep.RankingScreen) {
            postSideEffect(MainSideEffect.ClickRankingRetry)
        }
        reduce {
            state.copy(
                bottomNavigationStep = step,
            )
        }
    }

    /** 검색 화면으로 이동한다 */
    fun navigateToSearch(
        searchTag: String? = null,
    ) = intent {
        postSideEffect(MainSideEffect.NavigateToSearch(searchTag))
    }

    /** 홈 디테일 화면으로 이동한다 */
    fun navigateToHomeDetail(
        examId: Int,
    ) = intent {
        postSideEffect(
            MainSideEffect.NavigateToMainDetail(
                examId = examId,
            ),
        )
    }

    /** 문제 만들기 화면으로 이동한다 */
    fun navigateToCreateProblem() = intent {
        postSideEffect(MainSideEffect.NavigateToCreateProblem)
    }

    /** 친구 화면으로 이동한다 */
    fun navigateFriends(friendType: FriendsType, userId: Int) = intent {
        postSideEffect(MainSideEffect.NavigateToFriends(friendType, userId))
    }

    /** 설정 화면으로 이동한다 */
    fun navigateToSetting() = intent {
        postSideEffect(MainSideEffect.NavigateToSetting)
    }

    /** 알림 화면으로 이동한다 */
    fun navigateToNotification() = intent {
        postSideEffect(MainSideEffect.NavigateToNotification)
    }

    /** 온보딩(가이드) 활성화 여부를 업데이트한다 */
    fun updateGuideVisible(visible: Boolean) = intent {
        reduce { state.copy(guideVisible = visible) }
    }
}
