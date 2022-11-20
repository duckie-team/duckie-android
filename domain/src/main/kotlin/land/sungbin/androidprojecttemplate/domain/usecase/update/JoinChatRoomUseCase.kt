package land.sungbin.androidprojecttemplate.domain.usecase.update

import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.repository.UpdateRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class JoinChatRoomUseCase(
    private val repository: UpdateRepository,
) {
    /**
     * 주어진 유저를 특정 채팅방에 입장시킵니다.
     *
     * @param userId 채팅방에 입장할 [유저의 아이디][User.nickname]
     * @param roomId 입장할 [채팅방의 아이디][ChatRoom.id]
     * @return 입장 결과.
     * 입장 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다
     */
    suspend operator fun invoke(
        userId: String,
        roomId: String,
    ) = runDuckApiCatching {
        repository.joinChatRoom(
            userId = userId,
            roomId = roomId,
        )
    }
}
