/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.notification.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NotificationsResponse(
    @field:JsonProperty("notifications")
    val notifications: List<NotificationResponse>? = null,
)

data class NotificationResponse(
    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("body")
    val body: String? = null,

    @field:JsonProperty("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:JsonProperty("createdAt")
    val createdAt: String? = null,
)

