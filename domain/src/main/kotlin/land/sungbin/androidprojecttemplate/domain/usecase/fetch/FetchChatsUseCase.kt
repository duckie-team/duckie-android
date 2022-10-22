package land.sungbin.androidprojecttemplate.domain.usecase.fetch

import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.repository.DuckFetchRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class FetchChatsUseCase(
    private val repository: DuckFetchRepository,
) {
    /**
     * 특정 [채팅방][ChatRoom]에 전송된 [채팅][Chat] 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param chatRoomId 조회할 [채팅방 아이디][ChatRoom.id]
     * @return 조회된 [채팅][Chat] 목록을 담은 [fetch 결과][DuckFetchResult]
     */
    suspend operator fun invoke(
        @FK chatRoomId: String,
    ) = runDuckApiCatching {
        repository.fetchChats(
            chatRoomId = chatRoomId,
        )
    }
}
