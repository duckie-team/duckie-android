@file:Suppress(
    "ReplaceGetOrSet",
    "KDocFields",
)

package land.sungbin.androidprojecttemplate.data.mapper

import java.text.SimpleDateFormat
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
import land.sungbin.androidprojecttemplate.domain.model.constraint.ChatRoomType
import land.sungbin.androidprojecttemplate.domain.model.constraint.ChatType
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.domain.model.constraint.DislikeReason
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeReason
import land.sungbin.androidprojecttemplate.domain.model.constraint.Review
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported

private fun ContentData.toDomain() = Content(
    text = text.unwrap(
        field = "text",
    ),
    images = images.unwrap(
        field = "images",
    ),
    video = video,
)

private fun String.toDate() =
    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREA)
        .parse(this)
        .unwrap(
            field = "date",
        )

private fun DuckFeedCoreInformationData.toDomain(): DuckFeedCoreInformation {
    val (image, title, price) = this
    return DuckFeedCoreInformation(
        image = image.unwrap(
            field = "image",
        ),
        title = title.unwrap(
            field = "title",
        ),
        price = price.unwrap(
            field = "price",
        ).toInt(),
    )
}

internal fun ChatData.toDomain() = Chat(
    id = id.unwrap(
        field = "id",
    ),
    chatRoomId = chatRoomId.unwrap(
        field = "chatRoomId",
    ),
    sender = sender.unwrap(
        field = "sender",
    ),
    type = ChatType.values().get(
        type.unwrap(
            field = "type",
        )
    ),
    isDeleted = isDeleted.unwrap(
        field = "isDeleted",
    ),
    isEdited = isEdited.unwrap(
        field = "isEdited",
    ),
    content = content.unwrap(
        field = "content",
    ).toDomain(),
    sentAt = sentAt.unwrap(
        field = "sentAt",
    ).toDate(),
    duckFeedData = duckFeedDatas?.toDomain(),
)

@Unsupported
internal fun ChatReadData.toDomain() = ChatRead(
    chatRoomId = chatRoomId.unwrap(
        field = "chatRoomId",
    ),
    userId = userId.unwrap(
        field = "userId",
    ),
    lastestReadChatId = lastestReadChatId.unwrap(
        field = "lastestReadChatId",
    ),
)

internal fun ChatRoomData.toDomain() = ChatRoom(
    id = id.unwrap(
        field = "id",
    ),
    type = ChatRoomType.values().get(
        type.unwrap(
            field = "type",
        )
    ),
    coverImageUrl = coverImageUrl,
    name = name.unwrap(
        field = "name",
    ),
    ownerId = ownerId.unwrap(
        field = "ownerId",
    ),
    categories = categories?.map { categoryIndex ->
        Category.values()[categoryIndex]
    },
    tags = tags,
)

internal fun CommentData.toDomain() = Comment(
    id = id.unwrap(
        field = "id",
    ),
    parentId = parentId,
    ownerId = ownerId.unwrap(
        field = "userId",
    ),
    feedId = feedId.unwrap(
        field = "feedId",
    ),
    content = content.unwrap(
        field = "content",
    ).toDomain(),
    createdAt = createdAt.unwrap(
        field = "createdAt",
    ).toDate(),
)

@Unsupported
internal fun ContentStayTimeData.toDomain() = ContentStayTime(
    userId = userId.unwrap(
        field = "userId",
    ),
    categories = categories.unwrap(
        field = "categories",
    ),
    search = search.unwrap(
        field = "search",
    ),
    dm = dm.unwrap(
        field = "dm",
    ),
    notification = notification.unwrap(
        field = "notification",
    ),
)

internal fun DealReviewData.toDomain() = DealReview(
    id = id.unwrap(
        field = "id",
    ),
    buyerId = buyerId.unwrap(
        field = "buyerId",
    ),
    sellerId = sellerId.unwrap(
        field = "sellerId",
    ),
    feedId = feedId.unwrap(
        field = "feedId",
    ),
    isDirect = isDirect.unwrap(
        field = "isDirect",
    ),
    review = Review.values().get(
        review.unwrap(
            field = "review",
        )
    ),
    likeReasons = likeReasons.unwrap(
        field = "likeReason",
    ).map { reasonIndex ->
        LikeReason.values()[reasonIndex]
    },
    dislikeReasons = dislikeReasons.unwrap(
        field = "dislikeReason",
    ).map { reasonIndex ->
        DislikeReason.values()[reasonIndex]
    },
    etc = etc.unwrap(
        field = "etc",
    ),
)

internal fun FeedData.toDomain() = Feed(
    id = id.unwrap(
        field = "id",
    ),
    writerId = writerId.unwrap(
        field = "writerId",
    ),
    type = FeedType.values().get(
        type.unwrap(
            field = "type",
        )
    ),
    isDeleted = isDeleted.unwrap(
        field = "isDeleted",
    ),
    isHidden = isHidden.unwrap(
        field = "isHidden",
    ),
    content = content.unwrap(
        field = "content",
    ).toDomain(),
    categories = categories.unwrap(
        field = "categories",
    ).map { categoryIndex ->
        Category.values()[categoryIndex]
    },
    createdAt = createdAt.unwrap(
        field = "createdAt",
    ).toDate(),
    title = title,
    price = price,
    pushCount = pushCount,
    latestPushAt = latestPushAt,
    location = location,
    isDirectDealing = isDirectDealing,
    parcelable = parcelable,
    dealState = dealState?.let { stateIndex ->
        DealState.values()[stateIndex]
    },
)

@Unsupported
internal fun FeedScoreData.toDomain() = FeedScore(
    userId = userId.unwrap(
        field = "userId",
    ),
    feedId = feedId.unwrap(
        field = "feedId",
    ),
    stayTime = stayTime.unwrap(
        field = "stayTime",
    ),
    score = score.unwrap(
        field = "score",
    ),
)

internal fun FollowData.toDomain() = Follow(
    userId = accountId.unwrap(
        field = "accountId",
    ),
    followings = followings.unwrap(
        field = "followings",
    ),
    followers = followers.unwrap(
        field = "followers",
    ),
    blocks = blocks.unwrap(
        field = "blocks",
    ),
)

@Unsupported
internal fun HeartData.toDomain() = Heart(
    target = type.unwrap(
        field = "type",
    ),
    targetId = targetId.unwrap(
        field = "feedId",
    ),
    userId = userId.unwrap(
        field = "userId",
    ),
)

internal fun ReportData.toDomain() = Report(
    id = id.unwrap(
        field = "id",
    ),
    reporterId = reporterId.unwrap(
        field = "reporterId",
    ),
    targetId = targetId.unwrap(
        field = "targetId",
    ),
    targetContentId = targetFeedId,
    message = message.unwrap(
        field = "message",
    ),
    checked = checked.unwrap(
        field = "checked",
    ),
)

internal fun SaleRequestData.toDomain() = SaleRequest(
    id = id.unwrap(
        field = "id",
    ),
    feedId = feedId.unwrap(
        field = "feedId",
    ),
    ownerId = ownerId.unwrap(
        field = "ownerId"
    ),
    requesterId = requesterId.unwrap(
        field = "requesterId",
    ),
    result = result.unwrap(
        field = "result",
    ),
)

internal fun UserData.toDomain() = User(
    nickname = nickname.unwrap(
        field = "nickname",
    ),
    accountAvailable = accountAvailable.unwrap(
        field = "accountAvailable",
    ),
    profileUrl = profileUrl,
    tier = tier,
    badges = badges.unwrap(
        field = "badges",
    ).map { badgeIndex ->
        Badge.values()[badgeIndex]
    },
    likeCategories = likeCategories.unwrap(
        field = "likeCategories",
    ).map { categoryIndex ->
        Category.values()[categoryIndex]
    },
    interestedTags = interestedTags.unwrap(
        field = "interestedTags",
    ),
    nonInterestedTags = nonInterestedTags.unwrap(
        field = "nonInterestedTags",
    ),
    notificationTags = notificationTags.unwrap(
        field = "notificationTags",
    ),
    tradePreferenceTags = tradePreferenceTags.unwrap(
        field = "tradePreferenceTags",
    ),
    collections = collections.unwrap(
        field = "collections",
    ),
    createdAt = createdAt.unwrap(
        field = "createdAt",
    ).toDate(),
    deletedAt = deletedAt?.toDate(),
    bannedAt = bannedAt?.toDate(),
)
