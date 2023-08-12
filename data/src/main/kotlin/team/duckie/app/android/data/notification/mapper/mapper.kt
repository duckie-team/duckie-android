/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.notification.mapper

import team.duckie.app.android.data.notification.model.NotificationResponse
import team.duckie.app.android.data.notification.model.NotificationsResponse
import team.duckie.app.android.domain.notification.model.Notification
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap

internal fun NotificationsResponse.toDomain() =
    notifications?.fastMap(NotificationResponse::toDomain)
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.notifications")

internal fun NotificationResponse.toDomain() = Notification(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    title = title,
    body = body ?: duckieResponseFieldNpe("${this::class.java.simpleName}.body"),
    thumbnailUrl = thumbnailUrl
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.thumbnailUrl"),
    createdAt = createdAt
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.createdAt"),
)
