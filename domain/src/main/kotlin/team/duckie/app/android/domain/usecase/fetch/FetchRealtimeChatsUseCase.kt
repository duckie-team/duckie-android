package team.duckie.app.android.domain.usecase.fetch

import team.duckie.app.domain.model.Chat
import team.duckie.app.android.domain.model.ChatRoom
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.repository.FetchRepository

class FetchRealtimeChatsUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 특정 [채팅방][ChatRoom]에 전송된 [채팅][Chat] 목록을 실시간으로 조회합니다.
     *
     * @param chatRoomId 조회할 [채팅방 아이디][ChatRoom.id]
     * @param onNewChat 새로운 채팅이 조회될 때마다 실행될 콜백.
     * 콜백의 인자로는 [채팅][Chat] 이 전달됩니다.
     * @param onError 채팅 조회 도중에 에러가 발생할 때마다 실행될 콜백.
     * 콜백의 인자로는 [에러][Throwable] 가 전달됩니다.
     *
     * @return 실시간 [채팅][Chat] 조회를 종료하는 람다식.
     * **더 이상 실시간 [채팅][Chat] 조회가 필요 없을 때 무조건 실행해야 합니다.**
     * 그렇지 않으면 메모리 누수가 발생할 수 있습니다.
     */
    suspend operator fun invoke(
        @FK chatRoomId: String,
        onNewChat: (chat: Chat) -> Unit,
        onError: (error: Throwable) -> Unit,
    ) = repository.fetchRealtimeChats(
        chatRoomId = chatRoomId,
        onNewChat = onNewChat,
        onError = onError,
    )
}
