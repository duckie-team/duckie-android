package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import androidx.annotation.Size
import im.toss.util.tuid.tuid
import land.sungbin.androidprojecttemplate.domain.model.DealReview
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.DislikeReason
import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeReason
import land.sungbin.androidprojecttemplate.domain.model.constraint.Review
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class UpsertReviewUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [리뷰][Review] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param buyerId 구매자 [유저 아이디][User.nickname]
     * @param sellerId 판매자 [유저 아이디][User.nickname]
     * @param feedId 해당 거래가 진행된 [덕피드 아이디][Feed.id]
     * @param isDirect 직거래인지 여부
     * @param review 거래에 대한 종합적인 리뷰
     * @param likeReasons 좋았던 점 목록
     * @param dislikeReasons 아쉬웠던 점 목록
     * @param etc 기타 소견. 기본값은 null 이며, 공백일 수 있습니다.
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @FK buyerId: String,
        @FK sellerId: String,
        @FK feedId: String,
        isDirect: Boolean,
        review: Review,
        @Size(min = 1) likeReasons: List<LikeReason>,
        @Size(min = 1) dislikeReasons: List<DislikeReason>,
        etc: String? = null,
    ) = runDuckApiCatching {
        repository.upsertReview(
            review = DealReview(
                id = tuid(),
                buyerId = buyerId,
                sellerId = sellerId,
                feedId = feedId,
                isDirect = isDirect,
                review = review,
                likeReasons = likeReasons,
                dislikeReasons = dislikeReasons,
                etc = etc,
            ),
        )
    }
}
