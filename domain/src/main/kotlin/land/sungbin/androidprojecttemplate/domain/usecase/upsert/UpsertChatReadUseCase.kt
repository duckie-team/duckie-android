package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import im.toss.util.tuid.tuid
import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

@Unsupported
class UpsertChatReadUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [채팅 확인][ChatRead] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param userId [유저 아이디][User.nickname]
     * @param lastestReadChatId 마지막으로 읽은 [채팅 아이디][Chat.id]
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @PK @FK userId: String,
        @FK lastestReadChatId: String,
    ) = runDuckApiCatching {
        repository.upsertChatRead(
            chatRead = ChatRead(
                chatRoomId = tuid(),
                userId = userId,
                lastestReadChatId = lastestReadChatId,
            ),
        )
    }
}
