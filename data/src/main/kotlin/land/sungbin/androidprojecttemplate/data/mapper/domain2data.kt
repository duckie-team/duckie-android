package land.sungbin.androidprojecttemplate.data.mapper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import land.sungbin.androidprojecttemplate.data.model.ChatData
import land.sungbin.androidprojecttemplate.data.model.ChatReadData
import land.sungbin.androidprojecttemplate.data.model.ChatRoomData
import land.sungbin.androidprojecttemplate.data.model.CommentData
import land.sungbin.androidprojecttemplate.data.model.ContentStayTimeData
import land.sungbin.androidprojecttemplate.data.model.DealReviewData
import land.sungbin.androidprojecttemplate.data.model.DuckFeedCoreInformationData
import land.sungbin.androidprojecttemplate.data.model.FeedData
import land.sungbin.androidprojecttemplate.data.model.FeedScoreData
import land.sungbin.androidprojecttemplate.data.model.FollowData
import land.sungbin.androidprojecttemplate.data.model.HeartData
import land.sungbin.androidprojecttemplate.data.model.ReportData
import land.sungbin.androidprojecttemplate.data.model.SaleRequestData
import land.sungbin.androidprojecttemplate.data.model.UserData
import land.sungbin.androidprojecttemplate.data.model.common.ContentData
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
import land.sungbin.androidprojecttemplate.domain.model.Report
import land.sungbin.androidprojecttemplate.domain.model.SaleRequest
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.Badge
import land.sungbin.androidprojecttemplate.domain.model.constraint.Category
import land.sungbin.androidprojecttemplate.domain.model.constraint.DislikeReason
import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeReason

private fun Content.toData() = ContentData(
    text = text,
    images = images,
    video = video,
)

private fun Date.toDataString() =
    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREA).format(this)

private fun DuckFeedCoreInformation.toData() = DuckFeedCoreInformationData(
    images = images,
    title = title,
    price = price,
)

internal fun Chat.toData() = ChatData(
    id = id,
    chatRoomId = chatRoomId,
    sender = sender,
    type = type.index,
    isDeleted = isDeleted,
    isEdited = isEdited,
    content = content.toData(),
    sentAt = sentAt.toDataString(),
    duckFeedData = duckFeedData?.toData(),
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
    categories = categories?.map(Category::index),
    tags = tags,
)

internal fun Comment.toDat() = CommentData(
    id = id,
    parentId = parentId,
    userId = ownerId,
    content = content.toData(),
    createdAt = createdAt.toDataString(),
)

internal fun ContentStayTime.toData() = ContentStayTimeData(
    userId = userId,
    categories = categories,
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
    likeReason = likeReason.map(LikeReason::index),
    dislikeReason = dislikeReason.map(DislikeReason::index),
    etc = etc,
)

internal fun Feed.toData() = FeedData(
    id = id,
    writerId = writerId,
    type = type.index,
    isDeleted = isDeleted,
    isHidden = isHidden,
    content = content.toData(),
    categories = categories.map(Category::index),
    createdAt = createdAt.toDataString(),
    title = title,
    price = price,
    pushCount = pushCount,
    latestPushAt = latestPushAt,
    location = location,
    isDirectDealing = isDirectDealing,
    parcelable = parcelable,
    dealState = dealState?.index,
)

internal fun FeedScore.toData() = FeedScoreData(
    userId = userId,
    feedId = feedId,
    stayTime = stayTime,
    score = score,
)

internal fun Follow.toData() = FollowData(
    accountId = userId,
    followings = followings,
    followers = followers,
    blocks = blocks,
)

internal fun Heart.toData() = HeartData(
    type = type.index,
    feedId = commentId,
    userId = userId,
)

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
    nickname = nickname,
    accountAvailable = accountAvailable,
    profileUrl = profileUrl,
    tier = tier,
    badges = badges?.map(Badge::index),
    likeCategories = likeCategories.map(Category::index),
    interestedTags = interestedTags,
    nonInterestedTags = nonInterestedTags,
    notificationTags = notificationTags,
    tradePreferenceTags = tradePreferenceTags,
    collections = collections,
    createdAt = createdAt.toDataString(),
    deletedAt = deletedAt?.toDataString(),
    bannedAt = bannedAt?.toDataString(),
)
