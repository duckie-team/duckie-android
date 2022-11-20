package team.duckie.app.android.ui.main.notification

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.domain.model.NotificationItem
import team.duckie.app.ui.component.UiStatus

data class NotificationState(
    val uiStatus: UiStatus = UiStatus.Loading,
    val notifications: PersistentList<NotificationItem> = persistentListOf(),
)

sealed class NotificationSideEffect {
    class ShowToast(val message: String) : NotificationSideEffect()
}
