package land.sungbin.androidprojecttemplate.ui.main.notification

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import land.sungbin.androidprojecttemplate.domain.model.NotificationItem
import land.sungbin.androidprojecttemplate.ui.component.UiStatus

data class NotificationState(
    val uiStatus: UiStatus = UiStatus.Loading,
    val notifications: PersistentList<NotificationItem> = persistentListOf(),
)

sealed class NotificationSideEffect {
    class ShowToast(val message: String) : NotificationSideEffect()
}