package team.duckie.app.android.ui.main.home

import team.duckie.app.ui.component.UiStatus
import team.duckie.app.domain.model.Feed
import team.duckie.quackquack.ui.component.QuackBottomSheetItem

data class HomeState(
    val itemStatus: UiStatus = UiStatus.Loading,
    val feeds: List<Feed> = emptyList(),
    val filteredFeeds: List<Feed> = emptyList(),
    val selectedUser: String = "",
    val filterBottomSheetItems: List<QuackBottomSheetItem> = emptyList(),
    val moreBottomSheetItems: List<QuackBottomSheetItem> = emptyList(),
    val interestedTags: List<String> = emptyList(),
    val fabExpanded: Boolean = false,
)

