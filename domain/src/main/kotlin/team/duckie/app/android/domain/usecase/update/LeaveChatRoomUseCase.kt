package team.duckie.app.android.domain.usecase.update

import team.duckie.app.android.domain.model.ChatRoom
import team.duckie.app.domain.model.User
import team.duckie.app.domain.repository.UpdateRepository
import team.duckie.app.domain.repository.result.DuckApiResult

class LeaveChatRoomUseCase(
    private val repository: UpdateRepository,
) {
    /**
     * 주어진 유저를 특정 채팅방에서 퇴장시킵니다.
     *
     * @param userId 채팅방에서 퇴장할 [유저의 아이디][User.nickname]
     * @param roomId 퇴장할 [채팅방의 아이디][ChatRoom.id]
     * @return 퇴장 결과.
     * 퇴장 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다
     */
    suspend operator fun invoke(
        userId: String,
        roomId: String,
    ) = repository.leaveChatRoom(
        userId = userId,
        roomId = roomId,
    )
}
