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
    class ClickNewComment(val item: NotificationItem.NewComment) : NotificationSideEffect()

    class ClickNewHeart(val item: NotificationItem.NewHeart) : NotificationSideEffect()

    class ClickNewFollower(val item: NotificationItem.NewFollower) : NotificationSideEffect()

    class ClickRequireWriteReview(val item: NotificationItem.RequireWriteReview) :
        NotificationSideEffect()

    class ClickRequireChangeDealState(val item: NotificationItem.RequireChangeDealState) :
        NotificationSideEffect()

    class ClickRequireUpToDuckDeal(val item: NotificationItem.RequireUpToDuckDeal) :
        NotificationSideEffect()

    class ShowToast(val message: String) : NotificationSideEffect()
}