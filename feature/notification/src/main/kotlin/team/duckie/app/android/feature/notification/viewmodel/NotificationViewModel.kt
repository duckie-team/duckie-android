/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.notification.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.notification.model.Notification
import team.duckie.app.android.domain.notification.usecase.DeleteNotificationUseCase
import team.duckie.app.android.domain.notification.usecase.GetNotificationsUseCase
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase,
) : ViewModel(),
    ContainerHost<NotificationState, NotificationSideEffect> {
    override val container =
        container<NotificationState, NotificationSideEffect>(NotificationState())

    fun getNotifications() = intent {
        startLoading()
        getNotificationsUseCase()
            .onSuccess { notifications ->
                reduce {
                    state.copy(
                        isLoading = false,
                        isError = false,
                        notifications = notifications.toImmutableList(),
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(
                        isLoading = false,
                        isError = true,
                        notifications = emptyList<Notification>().toImmutableList(),
                    )
                }
            }
    }

    fun clickBackPress() = intent { postSideEffect(NotificationSideEffect.FinishActivity) }

    fun clickNotification(id: Int) = intent {
        deleteNotificationUseCase(id).onSuccess {
            postSideEffect(NotificationSideEffect.NavigateToMyPage)
        }.onFailure {
            postSideEffect(NotificationSideEffect.ReportError(it))
        }
    }

    private suspend fun startLoading() = intent {
        reduce {
            state.copy(isLoading = true, isError = false)
        }
    }
}
