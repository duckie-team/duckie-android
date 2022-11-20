package team.duckie.app.android.domain.usecase.score

import androidx.annotation.IntRange
import team.duckie.app.android.domain.model.ContentStayTime
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.constraint.Category
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.model.util.Unsupported
import team.duckie.app.domain.repository.ScoreRepository
import team.duckie.app.domain.repository.result.runDuckApiCatching

@Unsupported
class UpdateContentStayTimeUseCase(
    private val repository: ScoreRepository,
) {
    /**
     * [컨텐츠 별 머문 시간][ContentStayTime] 정보를 생성하거나 업데이트합니다.
     *
     * 모든 시간의 기준은 초를 사용합니다.
     * 또한 모든 시간 값의 필드는 선택적이며, 만약 해당 필드에 변경 사항이
     * 없다면 기본값인 null 을 그대로 제공해야 합니다.
     * 단, 모든 필드들 중에 최소 하나는 제공되야 합니다.
     *
     * @param userId [유저 아이디][User.nickname]
     * @param celebrity [Category.Celebrity] 에 머문 시간
     * @param movie [Category.Movie] 에 머문 시간
     * @param animation [Category.Animation] 에 머문 시간
     * @param webtoon [Category.WebToon] 에 머문 시간
     * @param game [Category.Game] 에 머문 시간
     * @param military [Category.Military] 에 머문 시간
     * @param it [Category.IT] 에 머문 시간
     * @param search 검색 화면에 머문 시간
     * @param dm DM 화면에 머문 시간
     * @param notification 알림 화면에 머문 시간
     *
     * @return 업데이트 성공 여부
     */
    suspend operator fun invoke(
        @PK @FK userId: String,
        @IntRange(from = 0) celebrity: Int? = null,
        @IntRange(from = 0) movie: Int? = null,
        @IntRange(from = 0) animation: Int? = null,
        @IntRange(from = 0) webtoon: Int? = null,
        @IntRange(from = 0) game: Int? = null,
        @IntRange(from = 0) military: Int? = null,
        @IntRange(from = 0) it: Int? = null,
        @IntRange(from = 0) search: Int? = null,
        @IntRange(from = 0) dm: Int? = null,
        @IntRange(from = 0) notification: Int? = null,
    ) = runDuckApiCatching {
        repository.updateContentStayTime(
            contentStayTime = team.duckie.app.android.domain.model.ContentStayTime(
                userId = userId,
                categories = listOf(
                    celebrity,
                    movie,
                    animation,
                    webtoon,
                    game,
                    military,
                    it,
                ),
                search = search,
                dm = dm,
                notification = notification,
            ),
        )
    }
}
