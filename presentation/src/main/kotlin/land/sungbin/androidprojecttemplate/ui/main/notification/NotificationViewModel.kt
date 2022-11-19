package land.sungbin.androidprojecttemplate.ui.main.notification

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.domain.model.NotificationItem
import land.sungbin.androidprojecttemplate.domain.repository.result.onExcepion
import land.sungbin.androidprojecttemplate.domain.repository.result.onSuccess
import land.sungbin.androidprojecttemplate.domain.usecase.fetch.FetchNotificationsUseCase
import land.sungbin.androidprojecttemplate.ui.component.UiStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationViewModel @Inject constructor(
    private val fetchNotificationsUseCase: FetchNotificationsUseCase,
) : BaseViewModel<NotificationState, NotificationSideEffect>(NotificationState()) {
    suspend fun fetch() {
        // TODO(riflockle7) 추후 유저 아이디 설정 필요
        val userId = "userId"

        // TODO(riflockle7) 이런 식으로 작성해도 동기 실행을 유지하는지 확인 필요
        updateState {
            copy(uiStatus = UiStatus.Loading)
        }
        fetchNotificationsUseCase(userId, true)
            .onSuccess { notifications ->
                updateState {
                    copy(
                        uiStatus = UiStatus.Success,
                        notifications = notifications.toPersistentList(),
                    )
                }
            }.onExcepion { message ->
                updateState {
                    copy(
                        uiStatus = UiStatus.Failed(message = message ?: "error"),
                        notifications = persistentListOf(),
                    )
                }
            }
    }

    suspend fun showToast(message: String) = postSideEffect {
        NotificationSideEffect.ShowToast(message)
    }

    suspend fun onClickItem(item: NotificationItem) = postSideEffect {
        when (item) {
            is NotificationItem.NewComment ->
                NotificationSideEffect.ClickNewComment(item)

            is NotificationItem.NewHeart ->
                NotificationSideEffect.ClickNewHeart(item)

            is NotificationItem.NewFollower ->
                NotificationSideEffect.ClickNewFollower(item)

            is NotificationItem.RequireWriteReview ->
                NotificationSideEffect.ClickRequireWriteReview(item)

            is NotificationItem.RequireChangeDealState ->
                NotificationSideEffect.ClickRequireChangeDealState(item)

            is NotificationItem.RequireUpToDuckDeal ->
                NotificationSideEffect.ClickRequireUpToDuckDeal(item)
        }
    }

    suspend fun onClickItemTargetUser(item: NotificationItem) = when (item) {
        is NotificationItem.NewComment -> showToast("${item.targetUserId} 클릭")
        is NotificationItem.NewHeart -> showToast("${item.targetUserId} 클릭")
        is NotificationItem.NewFollower -> showToast("${item.targetUserId} 클릭")
        is NotificationItem.RequireUpToDuckDeal -> showToast("${item.targetUserId} 클릭")
        else -> {}
    }

    suspend fun onClickItemDuckDeal(item: NotificationItem) = when (item) {
        is NotificationItem.RequireWriteReview -> showToast("${item.duckDealTitle} 클릭")
        is NotificationItem.RequireChangeDealState -> showToast("${item.duckDealTitle} 클릭")
        else -> {}
    }

    suspend fun onClickItemNewComment(item: NotificationItem.NewComment) =
        showToast("${item.type} 새 댓글 클릭")

    suspend fun onClickItemHeart(item: NotificationItem.NewHeart) =
        showToast("${item.type} 좋아요 클릭")

    suspend fun onClickItemFollower(item: NotificationItem.NewFollower) =
        showToast("${item.type} 팔로우 클릭")

    suspend fun onClickItemFollowToggle(item: NotificationItem.NewFollower) {
        // TODO(riflockle7) 이런 식으로 작성해도 동기 실행을 유지하는지 확인 필요
        updateState {
            val newNotifications = notifications.toMutableList()
            val index = notifications.indexOf(item)
            if (index == -1) {
                return@updateState copy()
            } else {
                val newNotification = item.copy(isFollowed = !item.isFollowed)
                newNotifications[index] = newNotification
                copy(notifications = newNotifications.toPersistentList())
            }
        }

        showToast("${item.type} 팔로우 토글 클릭")
    }

    suspend fun onClickItemReview(item: NotificationItem.RequireWriteReview) =
        showToast("${item.type} 구매 후기 클릭")

    suspend fun onClickItemDealFinish(item: NotificationItem.RequireChangeDealState) =
        showToast("${item.type} 거래완료 클릭")

    suspend fun onClickItemSaleRequest(item: NotificationItem.RequireUpToDuckDeal) =
        showToast("${item.type} 판매요청 클릭")
}