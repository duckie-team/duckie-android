package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.repository.FetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult

class FetchRealtimeChatsUseCase(
    private val repository: FetchRepository,
) {
    /**
     * 특정 [채팅방][ChatRoom]에 전송된 [채팅][Chat] 목록을 실시간으로 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param chatRoomId 조회할 [채팅방 아이디][ChatRoom.id]
     * @param onNewChat 새로운 채팅이 조회될 때마다 실행될 콜백.
     * 콜백의 인자로는 [채팅][Chat] 이 전달됩니다.
     * @param onError 채팅 조회 도중에 에러가 발생할 때마다 실행될 콜백.
     * 콜백의 인자로는 [에러][Throwable] 가 전달됩니다.
     *
     * @return API 호출 [결과][Result] 객체
     */
    suspend operator fun invoke(
        @FK chatRoomId: String,
        onNewChat: (chat: Chat) -> Unit,
        onError: (exception: Throwable) -> Unit,
    ) = runCatching {
        repository.fetchRealtimeChats(
            chatRoomId = chatRoomId,
            onNewChat = onNewChat,
            onError = onError,
        )
    }
}
