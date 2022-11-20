@file:Suppress("unused")

package team.duckie.app.android.domain.model.constraint

/**
 * 채팅방 타입
 */
enum class ChatRoomType(
    val index: Int,
    val description: String,
) {
    Normal(
        index = 0,
        description = "일반채팅",
    ),
    DuckDeal(
        index = 1,
        description = "덕딜채팅",
    ),
    Open(
        index = 2,
        description = "오픈채팅",
    ),
}
