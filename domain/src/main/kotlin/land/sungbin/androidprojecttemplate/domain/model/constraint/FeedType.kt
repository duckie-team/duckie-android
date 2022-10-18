@file:Suppress("unused")

package land.sungbin.androidprojecttemplate.domain.model.constraint

/** 피드 타입 */
enum class FeedType(
    val index: Int,
    val description: String,
) {
    DuckDeal(
        index = 0,
        description = "덕딜",
    ),
    Normal(
        index = 1,
        description = "덕피드",
    ),
}
