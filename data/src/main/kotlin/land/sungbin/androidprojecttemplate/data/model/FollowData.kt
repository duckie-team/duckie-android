package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.data.model.constraint.UsersData

internal data class FollowData(
    val account_id: String? = null,
    val followings: UsersData? = null,
    val followers: UsersData? = null,
    val blocks: UsersData? = null,
)
