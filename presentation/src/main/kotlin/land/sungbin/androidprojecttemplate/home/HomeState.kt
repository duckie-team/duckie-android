package land.sungbin.androidprojecttemplate.home

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.User
import team.duckie.quackquack.ui.component.QuackBottomSheetItem

data class HomeState(
    val status: UiStatus = UiStatus.Loading,
    val feeds: List<Feed> = emptyList(),
    val selectedUser: String = "",
    val filterBottomSheetItems: List<QuackBottomSheetItem> = emptyList(),
    val moreBottomSheetItems: List<QuackBottomSheetItem> = emptyList(),
    val interestedTags: List<String> = emptyList(),
)