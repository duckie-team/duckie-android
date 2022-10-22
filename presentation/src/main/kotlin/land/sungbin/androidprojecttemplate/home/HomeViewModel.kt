package land.sungbin.androidprojecttemplate.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
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
            delay(TestMilliSecond)
            reduce {
                state.copy(
                    itemStatus = UiStatus.Success,
                    feeds = dummyFeeds,
                    filteredFeeds = dummyFeeds,
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
        delay(TestMilliSecond)
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
                        0 -> QuackBottomSheetItem("${user}님 팔로우", item.isImportant)
                        1 -> QuackBottomSheetItem("${user}님 피드 차단", item.isImportant)
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
        var selectedIndex = 0
        reduce {
            state.copy(
                filterBottomSheetItems = state.filterBottomSheetItems.mapIndexed { index: Int, item: QuackBottomSheetItem ->
                    if (bottomSheetItem == item) {
                        selectedIndex = index
                        QuackBottomSheetItem(item.title, true)
                    } else {
                        QuackBottomSheetItem(item.title, false)
                    }
                }
            )
        }
        reduce {
            state.copy(filteredFeeds = state.feeds.filter { feed ->
                when (selectedIndex) {
                    1 -> feed.type == FeedType.Normal
                    2 -> feed.type == FeedType.DuckDeal
                    else -> true
                }
            })
        }
    }

    fun selectMoreBottomSheet(bottomSheetItem: QuackBottomSheetItem) = intent {
        when (state.moreBottomSheetItems.indexOf(bottomSheetItem)) {
            0 -> {}
            1 -> {}
            2 -> {}
        }
    }

    fun onFabMenuClick(index: Int) = intent {
        when (index) {
            0 -> postSideEffect(HomeSideEffect.NavigateToWriteFeed)
            1 -> postSideEffect(HomeSideEffect.NavigateToWriteDuckDeal)
        }
    }

    fun onHeartClick() = intent {

    }

    fun onCommentClick() = intent {

    }


    companion object {
        private const val TestMilliSecond = 1000L
    }
}