package land.sungbin.androidprojecttemplate.domain.model

import androidx.annotation.IntRange
import androidx.annotation.Size
import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.ChatType
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.New
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireRange
import land.sungbin.androidprojecttemplate.domain.model.util.requireSetting
import land.sungbin.androidprojecttemplate.domain.model.util.requireSize

/**
 * 채팅 모델
 *
 * @param id 고유 아이디
 * @param chatRoomId 해당 채팅이 전송된 [채팅방][ChatRoom]의 아이디
 * @param sender 채팅을 보낸 [유저][User]의 아이디
 * @param type 해당 채팅의 타입
 * @param isDeleted 삭제된 채팅인지 여부
 * @param isEdited 수정된 채팅인지 여부
 * @param content 채팅 내용
 * @param sentAt 채팅이 전송된 시간
 * @param duckFeedData 채팅에 표시될 덕딜피드의 정보.
 * [type] 이 [ChatType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [ChatType.DuckDeal] 이 아니라면 null 을 받습니다.
 */
@OptIn(
    New::class,
    Unsupported::class,
)
data class Chat(
    @PK val id: String,
    @FK val chatRoomId: String,
    @FK val sender: String,
    @Unsupported val type: ChatType = ChatType.DuckDeal,
    @Unsupported val isDeleted: Boolean? = null,
    @Unsupported val isEdited: Boolean? = null,
    val content: Content,
    val sentAt: Date,
    @New val duckFeedData: DuckFeedCoreInformation?,
) {
    init {
        requireInput(
            field = "id",
            value = id,
        )
        requireInput(
            field = "chatRoomId",
            value = chatRoomId,
        )
        requireInput(
            field = "sender",
            value = sender,
        )
        requireSetting(
            condition = type == ChatType.DuckDeal,
            trueConditionDescription = "type == ChatType.DuckDeal",
            field = "duckFeedData",
            value = duckFeedData,
        )
    }
}

/**
 * 채팅에 포함되는 덕딜 피드의 정보들 모델
 *
 * @param images 상품 이미지 주소 목록.
 * **최소 1장은 있어야 하며,** 한 이미지당 최대 5 MB 을 넘을 수 없습니다.
 * (한 이미지당 최대 용량은 조정 필요)
 * @param title 상품 이름
 * @param price 상품 가격
 */
data class DuckFeedCoreInformation(
    @Size(min = 1, max = 5) val images: List<String>,
    val title: String,
    @IntRange(from = 0) val price: Int,
) {
    init {
        requireSize(
            min = 1,
            max = 5,
            field = "images",
            value = images,
        )
        requireRange(
            min = 0,
            field = "price",
            value = price,
        )
        requireInput(
            field = "title",
            value = title,
        )
    }
}
