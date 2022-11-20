@file:Suppress("unused")

package team.duckie.app.android.domain.model.constraint

/** 좋아요 대상 */
enum class HeartTarget(
    val index: Int,
    val description: String,
) {
    Feed(
        index = 0,
        description = "피드",
    ),
    Comment(
        index = 1,
        description = "댓글",
    ),
}
