package team.duckie.app.android.domain.usecase.upsert

import im.toss.util.tuid.tuid
import java.util.Date
import team.duckie.app.domain.model.Chat
import team.duckie.app.android.domain.model.ChatRoom
import team.duckie.app.domain.model.DuckFeedCoreInformation
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.common.Content
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.repository.UpsertRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.runDuckApiCatching

class UpsertChatUseCase(
    private val repository: UpsertRepository,
) {
    /**
     * [채팅][Chat] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param chatRoomId 해당 채팅이 전송된 [채팅방][ChatRoom]의 아이디
     * @param sender 채팅을 보낸 [유저][User]의 아이디
     * @param content 채팅 내용
     * @param sentAt 채팅이 전송된 시간
     * @param duckFeedData 채팅에 표시될 덕딜피드의 정보
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @FK chatRoomId: String,
        @FK sender: String,
        content: Content,
        sentAt: Date,
        duckFeedData: DuckFeedCoreInformation,
    ) = runDuckApiCatching {
        repository.upsertChat(
            chat = Chat(
                id = tuid(),
                chatRoomId = chatRoomId,
                sender = sender,
                content = content,
                sentAt = sentAt,
                duckFeedData = duckFeedData,
            ),
        )
    }
}
