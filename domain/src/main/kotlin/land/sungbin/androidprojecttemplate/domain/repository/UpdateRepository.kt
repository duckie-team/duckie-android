package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult

/**
 * 특정 필드를 업데이트하는 API 들의 시그니처를 정의합니다.
 */
interface UpdateRepository : DuckRepository {
    /**
     * 주어진 유저를 특정 채팅방에 입장시킵니다.
     *
     * @param userId 채팅방에 입장할 [유저의 아이디][User.nickname]
     * @param roomId 입장할 [채팅방의 아이디][ChatRoom.id]
     * @return 입장 결과.
     * 입장 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다
     */
    suspend fun joinChatRoom(
        userId: String,
        roomId: String,
    ): DuckApiResult<Nothing>

    /**
     * 주어진 유저를 특정 채팅방에서 퇴장시킵니다.
     *
     * @param userId 채팅방에서 퇴장할 [유저의 아이디][User.nickname]
     * @param roomId 퇴장할 [채팅방의 아이디][ChatRoom.id]
     * @return 퇴장 결과.
     * 퇴장 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다
     */
    suspend fun leaveChatRoom(
        userId: String,
        roomId: String,
    ): DuckApiResult<Nothing>

    /**
     * 특정 댓글을 조건에 관계 없이 무조건 삭제합니다.
     *
     * @param commentId 삭제할 [댓글의 아이디][Comment.id]
     * @return 삭제 결과.
     * 삭제 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다
     */
    suspend fun deleteComment(
        commentId: String,
    ): DuckApiResult<Nothing>
}
