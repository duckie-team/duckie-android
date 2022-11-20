@file:Suppress("unused")

package land.sungbin.androidprojecttemplate.domain.model.constraint

/** 채팅 종류 */
enum class ChatType(
    val index: Int,
    val description: String,
) {
    Normal(
        index = 0,
        description = "일반",
    ),
    Place(
        index = 1,
        description = "장소",
    ),
    Promise(
        index = 2,
        description = "약속",
    ),
    DuckDeal(
        index = 3,
        description = "덕딜",
    ),
}
