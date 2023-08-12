/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.notification.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class Notification(
    val id: Int,
    val title: String?,
    val body: String,
    val thumbnailUrl: String,
    val createdAt: String,
) {
    companion object {
        fun empty(id: Int = 0) = Notification(
            id = id,
            title = "",
            body = "",
            thumbnailUrl = "",
            createdAt = "",
        )
    }
}
