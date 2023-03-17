/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.notification.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.notification.repository.NotificationRepository
import javax.inject.Inject

@Immutable
class DeleteNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository,
) {
    suspend operator fun invoke(id: Int) = runCatching {
        repository.deleteNotification(id)
    }
}
