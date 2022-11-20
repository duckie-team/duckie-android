package team.duckie.app.android.data.repository

import team.duckie.app.data.datasource.remote.NotificationDataSource
import team.duckie.app.domain.model.NotificationItem
import team.duckie.app.domain.model.User
import team.duckie.app.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource,
): NotificationRepository {
    /**
     * 주어진 아이디로부터 [알림][NotificationItem] 목록을 반환합니다.
     *
     * @param userId 조회할 [유저의 아이디][User.nickname]
     * @return 조회된 [알림][NotificationItem] 목록
     */
    override suspend fun fetchNotifications(
        userId: String,
    ) = notificationDataSource.getNotifications(
        userId = userId,
    )
}
