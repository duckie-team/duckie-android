@file:Suppress("unused")

package land.sungbin.androidprojecttemplate.domain.model.constraint

import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported

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
