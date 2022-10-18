package land.sungbin.androidprojecttemplate.home

import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.User

data class HomeState(
    val status: UiStatus = UiStatus.Loading,
    val user: User? = null,
    val follow: Follow? = null,
    val feeds: List<Feed> = emptyList(),
    val heart: List<Heart> = emptyList(),
    val comment: List<Comment> = emptyList(),
)