package land.sungbin.androidprojecttemplate.domain.model

import androidx.annotation.Size
import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireSetting
import land.sungbin.androidprojecttemplate.domain.model.util.requireSize

/**
 * 피드 모델
 *
 * [title] 인자를 포함한 [title] 다음에 나오는 모든 인자들은
 * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
 * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
 *
 * @param id 피드 고유 아이디
 * @param writerId 작성자 아이디
 * @param type 피드 타입
 * @param isHearted 사용자의 좋아요 여부
 * @param isDeleted 삭제됐는지 여부
 * @param isHidden 숨김처리 됐는지 여부
 * @param content 피드 내용
 * @param categories 피드 카테고리
 * @param createdAt 피드 생성 시간
 * @param title 상품명
 * @param pushCount 덕피드 끌어올리기 횟수
 * @param latestPushAt 덕피드 끌어올리기 최근 시간
 * @param price 상품 가격
 * @param location 직거래 위치
 * @param isDirectDealing 직거래 여부
 * @param parcelable 택배 거래 여부
 * @param dealState 거래 상태
 * @param hearts 좋아요 목록
 * @param comments 댓글 목록
 */
data class Feed(
    @PK val id: String,
    @FK val writerId: String,
    val type: FeedType,
    val isHearted: Boolean,
    val heartCount: Int,
    val commentCount: Int,
    val isDeleted: Boolean,
    @Unsupported val isHidden: Boolean? = null,
    val content: Content,
    @Size(min = 1) val categories: Categories,
    val createdAt: Date,
    val title: String?,
    val price: Int?,
    @Unsupported val pushCount: Int? = null,
    @Unsupported val latestPushAt: Date? = null,
    val location: String?,
    val isDirectDealing: Boolean?,
    val parcelable: Boolean?,
    val dealState: DealState?,
) {
    init {
        requireInput(
            field = "id",
            value = id,
        )
        requireInput(
            field = "writerId",
            value = writerId,
        )
        requireSize(
            min = 1,
            field = "categories",
            value = categories,
        )
        requireSetting(
            condition = type == FeedType.DuckDeal,
            trueConditionDescription = "type == FeedType.DuckDeal",
            field = "title",
            value = title,
        )
        requireSetting(
            condition = type == FeedType.DuckDeal,
            trueConditionDescription = "type == FeedType.DuckDeal",
            field = "price",
            value = price,
        )
        requireSetting(
            condition = type == FeedType.DuckDeal,
            trueConditionDescription = "type == FeedType.DuckDeal",
            field = "location",
            value = location,
        )
        requireSetting(
            condition = type == FeedType.DuckDeal,
            trueConditionDescription = "type == FeedType.DuckDeal",
            field = "isDirectDealing",
            value = isDirectDealing,
        )
        requireSetting(
            condition = type == FeedType.DuckDeal,
            trueConditionDescription = "type == FeedType.DuckDeal",
            field = "parcelable",
            value = parcelable,
        )
        requireSetting(
            condition = type == FeedType.DuckDeal,
            trueConditionDescription = "type == FeedType.DuckDeal",
            field = "dealState",
            value = dealState,
        )
    }
}




