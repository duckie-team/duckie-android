/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.compose.ui.dialog.ReportAlreadyExists
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.common.kotlin.exception.isReportAlreadyExists
import team.duckie.app.android.domain.report.usecase.ReportUseCase
import team.duckie.app.android.domain.tag.usecase.FetchPopularTagsUseCase
import team.duckie.app.android.feature.home.constants.BottomNavigationStep
import team.duckie.app.android.feature.home.constants.MainScreenType
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val fetchPopularTagsUseCase: FetchPopularTagsUseCase,
    private val reportUseCase: ReportUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState())

    // 각 화면 초기화 여부를 관리하는 Map
    private val initStateMap: MutableMap<MainScreenType, Boolean> =
        MainScreenType.values().associateWith { false }.toMutableMap()

    init {
        processDeepLink()
    }

    /** MainScreen 에 띄워지는 화면을 초기화한다. 이미 된 경우에는 하지 않는다. */
    fun initState(
        mainScreenType: MainScreenType,
        onInitStateMainScreen: () -> Unit,
    ) {
        if (initStateMap[mainScreenType] == false) {
            onInitStateMainScreen()
            initStateMap[mainScreenType] = true
        }
    }

    private fun processDeepLink() = intent {
        val examId = savedStateHandle.getStateFlow(Extras.DynamicLinkExamId, -1).value

        if (examId != -1) {
            postSideEffect(MainSideEffect.NavigateToMainDetailWithSingleTop(examId))
        }
    }

    /**
     * 신고할 게시물의 [examId] 를 업데이트합니다.
     */
    fun setTargetExamId(examId: Int) = intent {
        reduce {
            state.copy(targetExamId = examId)
        }
    }

    /**
     * [state.reportExamId]에 해당하는 게시물을 신고합니다.
     */
    fun report() = intent {
        reportUseCase(state.targetExamId)
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

    fun copyExamDynamicLink() = intent {
        val examId = state.targetExamId

        postSideEffect(MainSideEffect.CopyExamIdDynamicLink(examId))
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
        autoFocusing: Boolean = true,
    ) = intent {
        postSideEffect(MainSideEffect.NavigateToSearch(searchTag, autoFocusing))
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
    fun navigateFriends(friendType: FriendsType, userId: Int, nickname: String) = intent {
        postSideEffect(MainSideEffect.NavigateToFriends(friendType, userId, nickname))
    }

    /** 설정 화면으로 이동한다 */
    fun navigateToSetting() = intent {
        postSideEffect(MainSideEffect.NavigateToSetting)
    }

    /** 알림 화면으로 이동한다 */
    fun navigateToNotification() = intent {
        postSideEffect(MainSideEffect.NavigateToNotification)
    }

    fun navigateToProfile(userId: Int) = intent {
        postSideEffect(MainSideEffect.NavigateToProfile(userId))
    }

    /** 온보딩(가이드) 활성화 여부를 업데이트한다 */
    fun updateGuideVisible(visible: Boolean) = intent {
        reduce { state.copy(guideVisible = visible) }
    }
}
