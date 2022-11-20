package team.duckie.app.android.domain.usecase.upsert

import im.toss.util.tuid.tuid
import team.duckie.app.domain.model.Feed
import team.duckie.app.domain.model.SaleRequest
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.repository.UpsertRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.runDuckApiCatching

class UpsertSaleRequestUseCase(
    private val repository: UpsertRepository,
) {
    /**
     * [판매 요청][SaleRequest] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param feedId 판매 요청할 물품이 있는 [피드 아이디][Feed.id]
     * @param requesterId 물품 판매를 요청한 [유저 아이디][User.nickname]
     * @param result 판매 요청 수락 여부
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @FK feedId: String,
        @FK ownerId: String,
        @FK requesterId: String,
        result: Boolean,
    ) = runDuckApiCatching {
        repository.upsertSaleRequest(
            saleRequest = SaleRequest(
                id = tuid(),
                feedId = feedId,
                ownerId = ownerId,
                requesterId = requesterId,
                result = result,
            ),
        )
    }
}
