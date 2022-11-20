package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.NotificationItem
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckFetchResult

interface NotificationRepository {
    /**
     * 주어진 아이디로부터 [알림][NotificationItem] 목록을 조회합니다.
     *
     * 등록된 정보가 있다면 [DuckFetchResult.Success] 로 해당 값을 반환하고,
     * 그렇지 않다면 [DuckFetchResult.Empty] 를 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 [알림][NotificationItem] 목록 정보를 담은 [fetch 결과][DuckFetchResult]
     */
    suspend fun fetchNotifications(
        @PK userId: String,
    ): List<NotificationItem>
}