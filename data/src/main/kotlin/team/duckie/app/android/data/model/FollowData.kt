package team.duckie.app.android.data.model

import team.duckie.app.data.model.constraint.UsersData

internal data class FollowData(
    val account_id: String? = null,
    val followings: UsersData? = null,
    val followers: UsersData? = null,
    val blocks: UsersData? = null,
)
