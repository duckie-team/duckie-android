/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.notification.datasource

import team.duckie.app.android.domain.notification.model.Notification

interface NotificationDataSource {
    suspend fun getNotifications(): List<Notification>
    suspend fun deleteNotification(id: Int): Boolean
}
