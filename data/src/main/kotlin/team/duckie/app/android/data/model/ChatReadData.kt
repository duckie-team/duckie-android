package team.duckie.app.android.data.model

internal data class ChatReadData(
    val chatRoomId: String? = null,
    val userId: String? = null,
    val lastestReadChatId: String? = null,
)
