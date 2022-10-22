package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import androidx.annotation.IntRange
import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.Category
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

@Unsupported
class UpsertContentStayTimeUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [컨텐츠 별 머문 시간][ContentStayTime] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param userId [유저 아이디][User.nickname]
     * @param categories 각각 카테고리별 머문 시간.
     * [Categories] 타입이 아닌, **각각 카테고리별로 유저가 얼마나
     * 머물렀는지를 각각 인덱스로 받아야 합니다.**
     * 이용 가능한 카테고리는 [Category] 와 동일합니다.
     * @param search 검색 화면에 머문 시간
     * @param dm DM 화면에 머문 시간
     * @param notification 알림 화면에 머문 시간
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        userId: String,
        categories: List<Int>,
        @IntRange(from = 0) search: Int,
        @IntRange(from = 0) dm: Int,
        @IntRange(from = 0) notification: Int,
    ) = runDuckApiCatching {
        repository.upsertContentStayTime(
            contentStayTime = ContentStayTime(
                userId = userId,
                categories = categories,
                search = search,
                dm = dm,
                notification = notification,
            ),
        )
    }
}
