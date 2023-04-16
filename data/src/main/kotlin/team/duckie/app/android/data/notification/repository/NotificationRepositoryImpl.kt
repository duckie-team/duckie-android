/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.notification.repository

import team.duckie.app.android.data.notification.datasource.NotificationDataSource
import team.duckie.app.android.domain.notification.model.Notification
import team.duckie.app.android.domain.notification.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource,
) : NotificationRepository {
    override suspend fun getNotifications(): List<Notification> {
        return notificationDataSource.getNotifications()
    }

    override suspend fun deleteNotification(id: Int): Boolean {
        return notificationDataSource.deleteNotification(id)
    }
}
