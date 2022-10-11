package land.sungbin.androidprojecttemplate.domain.model

import land.sungbin.androidprojecttemplate.domain.model.util.requireInput

/**
 * 판매 요청 모델
 *
 * @param id 고유 아이디
 * @param feedId 판매 요청할 물품이 있는 [피드 아이디][Feed.id]
 * @param ownerId 해당 물품을 보유하고 있는 [유저 아이디][User.nickname]
 * @param requesterId 물품 판매를 요청한 [유저 아이디][User.nickname]
 * @param result 판매 요청 수락 여부
 */
data class SaleRequest(
    val id: String,
    val feedId: String,
    val ownerId: String,
    val requesterId: String,
    val result: Boolean,
) {
    init {
        requireInput(
            field = "id",
            value = id,
        )
        requireInput(
            field = "feedId",
            value = feedId,
        )
        requireInput(
            field = "ownerId",
            value = ownerId,
        )
        requireInput(
            field = "requesterId",
            value = requesterId,
        )
    }
}
