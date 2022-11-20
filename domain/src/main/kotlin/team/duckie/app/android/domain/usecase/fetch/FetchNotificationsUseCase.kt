package team.duckie.app.android.domain.usecase.fetch

import team.duckie.app.domain.model.NotificationItem
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.repository.NotificationRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.DuckFetchResult
import team.duckie.app.domain.repository.result.runDuckApiCatching
import team.duckie.app.domain.usecase.fetch.cache.CacheType
import team.duckie.app.domain.usecase.fetch.cache.invokeOrLoadCache

class FetchNotificationsUseCase(
    private val repository: NotificationRepository,
) {
    /**
     * [FetchNotificationsUseCase] 를 실행합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * 캐싱이 적용됩니다.
     *
     * @param userId 유저 아이디 (닉네임)
     * @param force 강제 요청 여부
     * @return repository 를 통해 요청하여 얻은 결과값
     */
    suspend operator fun invoke(
        @PK userId: String,
        force: Boolean = false,
    ): DuckApiResult<List<NotificationItem>> = invokeOrLoadCache(
        type = CacheType.Notification,
        pk = userId,
        forceUpdate = force,
    ) {
        runDuckApiCatching {
            DuckFetchResult.Success(
                value = repository.fetchNotifications(
                    userId = userId,
                ),
            )
        }
    }
}
