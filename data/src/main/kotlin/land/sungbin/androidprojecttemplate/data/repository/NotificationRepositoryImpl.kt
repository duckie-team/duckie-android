package land.sungbin.androidprojecttemplate.data.repository

import land.sungbin.androidprojecttemplate.data.datasource.remote.NotificationDataSource
import land.sungbin.androidprojecttemplate.domain.model.NotificationItem
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.repository.NotificationRepository
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