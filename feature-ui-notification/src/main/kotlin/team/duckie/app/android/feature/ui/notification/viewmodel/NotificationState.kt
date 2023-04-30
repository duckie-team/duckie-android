/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.notification.viewmodel

import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.notification.model.Notification

data class NotificationState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val notifications: ImmutableList<Notification> = skeletonNotifications,
)
