package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.HeartTarget
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported

internal interface HeartData {
    val type: HeartTarget
    val targetId: String?
    val userId: String?
}

@Unsupported
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
