package land.sungbin.androidprojecttemplate.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.home.component.dummyFeeds
import land.sungbin.androidprojecttemplate.home.component.dummyTags
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.quackquack.ui.component.QuackBottomSheetItem

class HomeViewModel : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container: Container<HomeState, HomeSideEffect> = container(HomeState())

    fun init() =
        intent {
            delay(2000L)
            reduce {
                state.copy(
                    itemStatus = UiStatus.Success,
                    feeds = dummyFeeds,
                    interestedTags = dummyTags,
                    filterBottomSheetItems = filterBottomSheetItems,
                    moreBottomSheetItems = moreBottomSheetItems,
                )
            }
        }


    fun refresh() = intent {
        reduce {
            state.copy(
                itemStatus = UiStatus.Loading,
            )
        }
        delay(1000L)
        reduce {
            state.copy(
                itemStatus = UiStatus.Success,
                feeds = dummyFeeds,
                interestedTags = dummyTags,
            )
        }
    }

    fun changeSelectedUser(user: String) = intent {
        reduce {
            state.copy(
                selectedUser = user,
                moreBottomSheetItems = state.moreBottomSheetItems.mapIndexed { index: Int, item: QuackBottomSheetItem ->
                    when (index) {
                        FollowIndex -> QuackBottomSheetItem("${user}님 팔로우", item.isImportant)
                        BlockFeedIndex -> QuackBottomSheetItem("${user}님 피드 차단", item.isImportant)
                        else -> QuackBottomSheetItem(item.title, item.isImportant)
                    }
                }
            )
        }
    }

    fun deleteTag(index: Int) = intent {
        reduce {
            state.copy(
                interestedTags = state.interestedTags - state.interestedTags[index]
            )
        }
    }

    fun selectFilterBottomSheet(bottomSheetItem: QuackBottomSheetItem) = intent {
        reduce {
            state.copy(
                filterBottomSheetItems = state.filterBottomSheetItems.map { item: QuackBottomSheetItem ->
                    if (bottomSheetItem == item) {
                        QuackBottomSheetItem(item.title, true)
                    } else {
                        QuackBottomSheetItem(item.title, false)
                    }
                }
            )
        }
    }

    fun selectMoreBottomSheet(bottomSheetItem: QuackBottomSheetItem) = intent {
        when (state.moreBottomSheetItems.indexOf(bottomSheetItem)) {
            FollowIndex -> {}
            BlockFeedIndex -> {}
            ReportIndex -> {}
        }
    }

    fun onFabMenuClick(index: Int) = intent {
        when (index) {
            FeedIndex -> postSideEffect(HomeSideEffect.NavigateToWriteFeed)
            DuckDealIndex -> postSideEffect(HomeSideEffect.NavigateToWriteDuckDeal)
        }
    }

    companion object {
        private const val FeedIndex = 0
        private const val DuckDealIndex = 1

        private const val FollowIndex = 0
        private const val BlockFeedIndex = 1
        private const val ReportIndex = 2
    }
}