/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.notification.datasource

import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data.notification.mapper.toDomain
import team.duckie.app.android.data.notification.model.NotificationsResponse
import team.duckie.app.android.domain.notification.model.Notification
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val fuel: Fuel,
) : NotificationDataSource {
    override suspend fun getNotifications(): List<Notification> = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .get("/notifications")
            .responseString()

        return@withContext responseCatchingFuel(
            response = response,
            parse = NotificationsResponse::toDomain,
        )
    }

    override suspend fun deleteNotification(id: Int): Boolean = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .delete(
                path = "/notifications",
                parameters = listOf("id" to id),
            ).responseString()

        return@withContext responseCatchingFuel(
            response = response,
            parse = { isSuccess: Boolean -> isSuccess },
        )
    }
}
