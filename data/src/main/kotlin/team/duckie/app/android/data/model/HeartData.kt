@file:Suppress("PropertyName")

package team.duckie.app.android.data.model

import team.duckie.app.domain.model.constraint.HeartTarget
import team.duckie.app.domain.model.util.Unsupported

internal interface HeartData {
    val type: HeartTarget
    val target_id: String?
    val user_id: String?
}

@Unsupported
internal data class CommentHeartData(
    override val target_id: String? = null,
    override val user_id: String? = null,
) : HeartData {
    override val type = HeartTarget.Comment
}

internal data class FeedHeartData(
    override val target_id: String? = null,
    override val user_id: String? = null,
) : HeartData {
    override val type = HeartTarget.Feed
}
