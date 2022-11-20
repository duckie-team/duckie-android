@file:Suppress("KDocFields")
@file:OptIn(Unsupported::class)

package land.sungbin.androidprojecttemplate.data.mapper

import land.sungbin.androidprojecttemplate.data.model.AccountInformationData
import land.sungbin.androidprojecttemplate.data.model.ChatData
import land.sungbin.androidprojecttemplate.data.model.ChatReadData
import land.sungbin.androidprojecttemplate.data.model.ChatRoomData
import land.sungbin.androidprojecttemplate.data.model.CommentData
import land.sungbin.androidprojecttemplate.data.model.CommentHeartData
import land.sungbin.androidprojecttemplate.data.model.ContentStayTimeData
import land.sungbin.androidprojecttemplate.data.model.DealReviewData
import land.sungbin.androidprojecttemplate.data.model.FeedData
import land.sungbin.androidprojecttemplate.data.model.FeedHeartData
import land.sungbin.androidprojecttemplate.data.model.FeedScoreData
import land.sungbin.androidprojecttemplate.data.model.FollowData
import land.sungbin.androidprojecttemplate.data.model.HeartData
import land.sungbin.androidprojecttemplate.data.model.NotificationData
import land.sungbin.androidprojecttemplate.data.model.ReportData
import land.sungbin.androidprojecttemplate.data.model.SaleRequestData
import land.sungbin.androidprojecttemplate.data.model.SettingData
import land.sungbin.androidprojecttemplate.data.model.UserData
import land.sungbin.androidprojecttemplate.data.model.common.ContentData
import land.sungbin.androidprojecttemplate.domain.model.AccountInformationEntity
import land.sungbin.androidprojecttemplate.data.model.constraint.CategoriesData
import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.ContentStayTime
import land.sungbin.androidprojecttemplate.domain.model.DealReview
import land.sungbin.androidprojecttemplate.domain.model.DuckFeedCoreInformation
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedScore
import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.NotificationItem
import land.sungbin.androidprojecttemplate.domain.model.Report
import land.sungbin.androidprojecttemplate.domain.model.SaleRequest
import land.sungbin.androidprojecttemplate.domain.model.SettingEntity
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.Badge
import land.sungbin.androidprojecttemplate.domain.model.constraint.Category
import land.sungbin.androidprojecttemplate.domain.model.constraint.DislikeReason
import land.sungbin.androidprojecttemplate.domain.model.constraint.HeartTarget
import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeReason
import land.sungbin.androidprojecttemplate.domain.model.util.NewField
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
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

internal fun ChatRead.toData() = ChatReadData(
    chatRoomId = chatRoomId,
    userId = userId,
    lastestReadChatId = lastestReadChatId,
)

internal fun ChatRoom.toData() = ChatRoomData(
    id = id,
    type = type.index,
    coverImageUrl = coverImageUrl,
    name = name,
    ownerId = ownerId,
    categories = categories?.map(Category::index),
    tags = tags,
)

internal fun Comment.toDat() = CommentData(
    id = id,
    parentId = parentId,
    ownerId = ownerId,
    feedId = feedId,
    content = content.toData(),
    createdAt = createdAt.toDataString(),
)

internal fun ContentStayTime.toData() = ContentStayTimeData(
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

internal fun AccountInformationEntity.toData() = AccountInformationData(
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
