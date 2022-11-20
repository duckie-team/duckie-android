@file:Suppress("PropertyName")

package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.HeartTarget
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported

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
