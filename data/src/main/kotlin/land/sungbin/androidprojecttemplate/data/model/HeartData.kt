package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.HeartTarget

internal interface HeartData {
    val type: HeartTarget
    val targetId: String?
    val userId: String?
}

internal data class CommentHeartData(
    override val targetId: String? = null,
    override val userId: String? = null,
) : HeartData {
    override val type = HeartTarget.Comment
}

internal data class FeedHeartData(
    override val targetId: String? = null,
    override val userId: String? = null,
) : HeartData {
    override val type = HeartTarget.Feed
}
