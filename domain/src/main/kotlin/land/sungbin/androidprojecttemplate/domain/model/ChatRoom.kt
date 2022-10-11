package land.sungbin.androidprojecttemplate.domain.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.Tags
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireSetting

/**
 * 채팅방 모델
 *
 * @param id 고유 아이디
 * @param type 채팅방 타입
 * @param coverImageUrl 채팅방 커버 이미지 주소.
 * null 은 설정된 커버 이미지가 없음을 의미합니다.
 * @param name 채팅방 이름
 * @param categories 채팅방 카테고리.
 * [type] 이 [ChatRoomType.Open] 일 때만 유효합니다.
 * [type] 이 [ChatRoomType.Open] 이 아니라면 null 을 받습니다.
 * @param tags 채팅방 태그.
 * [type] 이 [ChatRoomType.Open] 일 때만 유효합니다.
 * [type] 이 [ChatRoomType.Open] 이 아니라면 null 을 받습니다.
 */
data class ChatRoom(
    val id: String,
    val type: ChatRoomType,
    val coverImageUrl: String?,
    val name: String,
    val categories: Categories?,
    val tags: Tags?,
) {
    init {
        requireInput(
            field = "id",
            value = id,
        )
        requireInput(
            field = "name",
            value = name,
        )
        requireSetting(
            condition = type == ChatRoomType.Open,
            trueConditionDescription = "type == ChatRoomType.Open",
            field = "categories",
            value = categories,
        )
        requireSetting(
            condition = type == ChatRoomType.Open,
            trueConditionDescription = "type == ChatRoomType.Open",
            field = "tags",
            value = tags,
        )
    }
}

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
