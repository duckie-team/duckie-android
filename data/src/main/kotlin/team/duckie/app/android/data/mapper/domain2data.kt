@file:Suppress("KDocFields")
@file:OptIn(Unsupported::class)

package team.duckie.app.android.data.mapper

import team.duckie.app.data.model.AccountInformationData
import team.duckie.app.data.model.ChatData
import team.duckie.app.data.model.ChatReadData
import team.duckie.app.data.model.ChatRoomData
import team.duckie.app.data.model.CommentData
import team.duckie.app.data.model.CommentHeartData
import team.duckie.app.data.model.ContentStayTimeData
import team.duckie.app.data.model.DealReviewData
import team.duckie.app.data.model.FeedData
import team.duckie.app.data.model.FeedHeartData
import team.duckie.app.data.model.FeedScoreData
import team.duckie.app.data.model.FollowData
import team.duckie.app.data.model.HeartData
import team.duckie.app.data.model.NotificationData
import team.duckie.app.data.model.ReportData
import team.duckie.app.data.model.SaleRequestData
import team.duckie.app.data.model.SettingData
import team.duckie.app.data.model.UserData
import team.duckie.app.data.model.common.ContentData
import team.duckie.app.android.domain.model.AccountInformationEntity
import team.duckie.app.domain.model.Chat
import team.duckie.app.android.domain.model.ChatRead
import team.duckie.app.android.domain.model.ChatRoom
import team.duckie.app.android.domain.model.Comment
import team.duckie.app.android.domain.model.ContentStayTime
import team.duckie.app.domain.model.DealReview
import team.duckie.app.domain.model.DuckFeedCoreInformation
import team.duckie.app.domain.model.Feed
import team.duckie.app.domain.model.FeedScore
import team.duckie.app.domain.model.Follow
import team.duckie.app.domain.model.Heart
import team.duckie.app.domain.model.NotificationItem
import team.duckie.app.domain.model.Report
import team.duckie.app.domain.model.SaleRequest
import team.duckie.app.domain.model.SettingEntity
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.common.Content
import team.duckie.app.domain.model.constraint.Badge
import team.duckie.app.domain.model.constraint.Category
import team.duckie.app.domain.model.constraint.DislikeReason
import team.duckie.app.domain.model.constraint.HeartTarget
import team.duckie.app.domain.model.constraint.LikeReason
import team.duckie.app.domain.model.util.NewField
import team.duckie.app.domain.model.util.Unsupported
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun Content.toData() = ContentData(
    text = text,
    images = images,
    video = video,
)

private fun Date.toDataString() =
    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREA).format(this)

private fun DuckFeedCoreInformation.toData() = listOf(
    image,
    title,
    price.toString(),
)

@OptIn(NewField::class)
internal fun Chat.toData() = ChatData(
    id = id,
    chatRoomId = chatRoomId,
    sender = sender,
    type = type.index,
    isDeleted = isDeleted,
    isEdited = isEdited,
    content = content.toData(),
    sentAt = sentAt.toDataString(),
    duckFeedDatas = duckFeedData?.toData(),
)

internal fun team.duckie.app.android.domain.model.ChatRead.toData() = ChatReadData(
    chatRoomId = chatRoomId,
    userId = userId,
    lastestReadChatId = lastestReadChatId,
)

internal fun team.duckie.app.android.domain.model.ChatRoom.toData() = ChatRoomData(
    id = id,
    type = type.index,
    coverImageUrl = coverImageUrl,
    name = name,
    ownerId = ownerId,
    categories = categories?.map(Category::index),
    tags = tags,
)

internal fun team.duckie.app.android.domain.model.Comment.toDat() = CommentData(
    id = id,
    parentId = parentId,
    ownerId = ownerId,
    feedId = feedId,
    content = content.toData(),
    createdAt = createdAt.toDataString(),
)

internal fun team.duckie.app.android.domain.model.ContentStayTime.toData() = ContentStayTimeData(
    user_id = userId,
    categories = categories.requireNoNulls(),
    search = search,
    dm = dm,
    notification = notification,
)

internal fun DealReview.toData() = DealReviewData(
    id = id,
    buyerId = buyerId,
    sellerId = sellerId,
    feedId = feedId,
    isDirect = isDirect,
    review = review.index,
    likeReasons = likeReasons.map(LikeReason::index),
    dislikeReasons = dislikeReasons.map(DislikeReason::index),
    etc = etc,
)

internal fun Feed.toData() = FeedData(
    id = id,
    writer_id = writerId,
    type = type.index,
    is_delete = isDeleted,
    is_hidden = isHidden,
    content = content.toData(),
    categories = categories.map(Category::index),
    create_at = createdAt.toDataString(),
    title = title,
    price = price,
    push_count = pushCount,
    lastest_push_at = latestPushAt,
    location = location,
    is_direct_dealing = isDirectDealing,
    parcelable = parcelable,
    deal_state = dealState?.index,
)

internal fun FeedScore.toData() = FeedScoreData(
    user_id = userId,
    feed_id = feedId,
    stay_time = stayTime,
    score = score,
)

internal fun Follow.toData() = FollowData(
    account_id = userId,
    followings = followings,
    followers = followers,
    blocks = blocks,
)

@OptIn(NewField::class)
internal fun Heart.toData(): HeartData {
    return when (target) {
        HeartTarget.Feed -> FeedHeartData(
            user_id = userId,
            target_id = targetId,
        )
        HeartTarget.Comment -> CommentHeartData(
            user_id = userId,
            target_id = targetId,
        )
    }
}

internal fun Report.toData() = ReportData(
    id = id,
    reporterId = reporterId,
    targetId = targetId,
    targetFeedId = targetContentId,
    message = message,
    checked = checked,
)

internal fun SaleRequest.toData() = SaleRequestData(
    id = id,
    feedId = feedId,
    ownerId = ownerId,
    requesterId = requesterId,
    result = result,
)

internal fun User.toData() = UserData(
    nick_name = nickname,
    account_enabled = accountAvailable,
    profile_url = profileUrl,
    tier = tier,
    badges = badges?.map(Badge::index),
    like_categories = likeCategories.map(Category::index),
    interested_tags = interestedTags,
    non_interested_tags = nonInterestedTags,
    notification_tags = notificationTags,
    trade_preference_tags = tradePreferenceTags,
    collections = collections,
    create_at = createdAt.toDataString(),
    delete_at = deletedAt?.toDataString(),
    banned_at = bannedAt?.toDataString(),
)

internal fun SettingEntity.toData() = SettingData(
    activityNotification = activityNotification,
    messageNotification = messageNotification,
)

internal fun team.duckie.app.android.domain.model.AccountInformationEntity.toData() = AccountInformationData(
    accountType = accountType,
    email = email,
)

internal fun NotificationItem.toData(): NotificationData = when(this) {
    is NotificationItem.NewComment -> NotificationData(
        type = this.type.key,
        id = this.id,
        profileUrl = "${this.profileUrl}",
        createdAt = this.createdAt,
        description = this.description,
        targetUserId = this.targetUserId,
        feedId = this.feedId,
    )
    is NotificationItem.NewHeart -> NotificationData(
        type = this.type.key,
        id = this.id,
        profileUrl = "${this.profileUrl}",
        createdAt = this.createdAt,
        description = this.description,
        targetUserId = this.targetUserId,
        feedId = this.feedId,
    )
    is NotificationItem.NewFollower -> NotificationData(
        type = this.type.key,
        id = this.id,
        profileUrl = "${this.profileUrl}",
        createdAt = this.createdAt,
        targetUserId = this.targetUserId,
        isFollowed = this.isFollowed,
    )
    is NotificationItem.RequireWriteReview -> NotificationData(
        type = this.type.key,
        id = this.id,
        profileUrl = "${this.profileUrl}",
        createdAt = this.createdAt,
        duckDealTitle = this.duckDealTitle,
        duckDealUrl = this.duckDealUrl,
    )
    is NotificationItem.RequireChangeDealState -> NotificationData(
        type = this.type.key,
        id = this.id,
        profileUrl = "${this.profileUrl}",
        createdAt = this.createdAt,
        duckDealTitle = this.duckDealTitle,
        duckDealUrl = this.duckDealUrl,
    )
    is NotificationItem.RequireUpToDuckDeal -> NotificationData(
        type = this.type.key,
        id = this.id,
        profileUrl = "${this.profileUrl}",
        createdAt = this.createdAt,
        targetUserId = this.targetUserId,
        duckDealUrl = this.duckDealUrl,
    )
}
