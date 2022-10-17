package land.sungbin.androidprojecttemplate.domain.model

import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput

/**
 * 채팅 확인 모델
 *
 * @param chatRoomId [채팅방 아이디][ChatRoom.id]
 * @param userId [유저 아이디][User.nickname]
 * @param lastestReadChatId 마지막으로 읽은 [채팅 아이디][Chat.id]
 */
@Suppress("DataClassPrivateConstructor")
data class ChatRead @Unsupported private constructor(
    @PK @FK val chatRoomId: String,
    @PK @FK val userId: String,
    @FK val lastestReadChatId: String,
) {
    init {
        requireInput(
            field = "chatRoomId",
            value = chatRoomId,
        )
        requireInput(
            field = "userId",
            value = userId,
        )
        requireInput(
            field = "lastestReadChatId",
            value = lastestReadChatId,
        )
    }
}
