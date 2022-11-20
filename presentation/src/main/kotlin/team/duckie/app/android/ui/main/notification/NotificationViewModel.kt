package team.duckie.app.android.ui.main.notification

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.base.BaseViewModel
import team.duckie.app.domain.model.NotificationItem
import team.duckie.app.domain.repository.result.onExcepion
import team.duckie.app.domain.repository.result.onSuccess
import team.duckie.app.domain.usecase.fetch.FetchNotificationsUseCase
import team.duckie.app.ui.component.UiStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationViewModel @Inject constructor(
    private val fetchNotificationsUseCase: FetchNotificationsUseCase,
) : BaseViewModel<NotificationState, NotificationSideEffect>(NotificationState()) {
    suspend fun fetch() {
        // TODO(riflockle7) 추후 유저 아이디 설정 필요
        val userId = "userId"

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

    suspend fun onClickItem(item: NotificationItem) = when (item) {
        is NotificationItem.NewComment -> {
            showToast("${item.type} 항목 클릭")
        }

        is NotificationItem.NewHeart -> {
            showToast("${item.type} 항목 클릭")
        }

        is NotificationItem.NewFollower -> {
            showToast("${item.type} 항목 클릭")
        }

        is NotificationItem.RequireWriteReview -> {
            showToast("${item.type} 항목 클릭")
        }

        is NotificationItem.RequireChangeDealState -> {
            showToast("${item.type} 항목 클릭")
        }

        is NotificationItem.RequireUpToDuckDeal -> {
            showToast("${item.type} 항목 클릭")
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

    suspend fun onClickItemNewComment(item: NotificationItem.NewComment) {
        postSideEffect { NotificationSideEffect.ShowToast("$item") }
    }

    suspend fun onClickItemHeart(item: NotificationItem.NewHeart) =
        showToast("${item.type} 좋아요 클릭")

    suspend fun onClickItemFollower(item: NotificationItem.NewFollower) =
        showToast("${item.type} 팔로우 클릭")

    suspend fun onClickItemFollowToggle(item: NotificationItem.NewFollower) {
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
