package land.sungbin.androidprojecttemplate.home

import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.domain.model.Feed
import team.duckie.quackquack.ui.component.QuackBottomSheetItem

data class HomeState(
    val itemStatus: UiStatus = UiStatus.Loading,
    val feeds: List<Feed> = emptyList(),
    val selectedUser: String = "",
    val filterBottomSheetItems: List<QuackBottomSheetItem> = emptyList(),
    val moreBottomSheetItems: List<QuackBottomSheetItem> = emptyList(),
    val interestedTags: List<String> = emptyList(),
)

