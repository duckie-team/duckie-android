package land.sungbin.androidprojecttemplate.home

import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.domain.model.Feed

data class HomeState(
    val status: UiStatus = UiStatus.Loading,
    val feeds: List<Feed> = emptyList(),
)