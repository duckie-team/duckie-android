package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import androidx.annotation.Size
import im.toss.util.tuid.tuid
import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class UpsertFeedUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [피드][Feed] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * [title] 인자를 포함한 [title] 다음에 나오는 모든 인자들은
     * [type] 이 [FeedType.DuckDeal] 일 때만 유효합니다.
     * [type] 이 [FeedType.DuckDeal] 이 아니라면 null 을 받습니다.
     *
     * @param writerId 작성자 아이디
     * @param type 피드 타입
     * @param isDeleted 삭제됐는지 여부
     * @param content 피드 내용
     * @param categories 피드 카테고리
     * @param createdAt 피드 생성 시간
     * @param title 상품명
     * @param price 상품 가격
     * @param location 직거래 위치
     * @param isDirectDealing 직거래 여부
     * @param parcelable 택배 거래 여부
     * @param dealState 거래 상태
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @FK writerId: String,
        type: FeedType,
        isDeleted: Boolean,
        content: Content,
        @Size(min = 1) categories: Categories,
        createdAt: Date,
        title: String?,
        price: Int?,
        location: String?,
        isDirectDealing: Boolean?,
        parcelable: Boolean?,
        dealState: DealState?,
    ) = runDuckApiCatching {
        repository.upsertFeed(
            feed = Feed(
                id = tuid(),
                writerId = writerId,
                type = type,
                isDeleted = isDeleted,
                content = content,
                categories = categories,
                createdAt = createdAt,
                title = title,
                price = price,
                location = location,
                isDirectDealing = isDirectDealing,
                parcelable = parcelable,
                dealState = dealState,
            ),
        )
    }
}
