package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import im.toss.util.tuid.tuid
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.repository.UpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class UpsertChatRoomUseCase(
    private val repository: UpsertRepository,
) {
    /**
     * [채팅방][ChatRoom] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param coverImageUrl 채팅방 커버 이미지 주소.
     * null 은 설정된 커버 이미지가 없음을 의미합니다.
     * @param name 채팅방 이름
     * @param ownerId 채팅방 방장 [유저 아이디][User.nickname]
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        coverImageUrl: String?,
        name: String,
        @FK ownerId: String,
    ) = runDuckApiCatching {
        repository.upsertChatRoom(
            chatRoom = ChatRoom(
                id = tuid(),
                coverImageUrl = coverImageUrl,
                name = name,
                ownerId = ownerId,
                categories = null,
                tags = null,
            ),
        )
    }
}
