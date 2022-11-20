@file:Suppress(
    "ReplaceGetOrSet",
    "KDocFields",
)

package team.duckie.app.android.data.mapper

import team.duckie.app.data.model.AccountInformationData
import team.duckie.app.data.model.ChatData
import team.duckie.app.data.model.ChatReadData
import team.duckie.app.data.model.ChatRoomData
import team.duckie.app.data.model.CommentData
import team.duckie.app.data.model.ContentStayTimeData
import team.duckie.app.data.model.DealReviewData
import team.duckie.app.data.model.DuckFeedCoreInformationData
import team.duckie.app.data.model.FeedData
import team.duckie.app.data.model.FeedScoreData
import team.duckie.app.data.model.FollowData
import team.duckie.app.data.model.HeartData
import team.duckie.app.data.model.LikeCategoryData
import team.duckie.app.data.model.NotificationData
import team.duckie.app.data.model.ReportData
import team.duckie.app.data.model.SaleRequestData
import team.duckie.app.data.model.SettingData
import team.duckie.app.data.model.UserData
import team.duckie.app.data.model.auth.KakaoLoginResponseData
import team.duckie.app.data.model.auth.LoginResponseData
import team.duckie.app.data.model.auth.LoginUserData
import team.duckie.app.data.model.auth.SignUpResponseData
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
import team.duckie.app.domain.model.KakaoLoginResponse
import team.duckie.app.domain.model.LoginResponse
import team.duckie.app.domain.model.LoginUser
import team.duckie.app.domain.model.NotificationItem
import team.duckie.app.domain.model.NotificationType
import team.duckie.app.domain.model.Report
import team.duckie.app.domain.model.SaleRequest
import team.duckie.app.domain.model.SettingEntity
import team.duckie.app.domain.model.SignUpResponse
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.common.Content
import team.duckie.app.domain.model.constraint.Badge
import team.duckie.app.domain.model.constraint.Category
import team.duckie.app.domain.model.constraint.ChatRoomType
import team.duckie.app.domain.model.constraint.ChatType
import team.duckie.app.domain.model.constraint.DealState
import team.duckie.app.domain.model.constraint.DislikeReason
import team.duckie.app.domain.model.constraint.FeedType
import team.duckie.app.domain.model.constraint.LikeCategory
import team.duckie.app.domain.model.constraint.LikeReason
import team.duckie.app.domain.model.constraint.Review
import team.duckie.app.domain.model.util.Unsupported
import java.text.SimpleDateFormat
import java.util.Locale

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
internal fun ChatReadData.toDomain() = team.duckie.app.android.domain.model.ChatRead(
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

internal fun ChatRoomData.toDomain() = team.duckie.app.android.domain.model.ChatRoom(
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
    joinUsers = emptyList(),
)

internal fun CommentData.toDomain() = team.duckie.app.android.domain.model.Comment(
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
internal fun ContentStayTimeData.toDomain() = team.duckie.app.android.domain.model.ContentStayTime(
    userId = user_id.unwrap(
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
    writerId = writer_id.unwrap(
        field = "writerId",
    ),
    type = FeedType.values().get(
        type.unwrap(
            field = "type",
        )
    ),
    isDeleted = is_delete.unwrap(
        field = "isDeleted",
    ),
    isHidden = is_hidden.unwrap(
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
    createdAt = create_at.unwrap(
        field = "createdAt",
    ).toDate(),
    title = title,
    price = price,
    pushCount = push_count,
    latestPushAt = lastest_push_at,
    location = location,
    isDirectDealing = is_direct_dealing,
    parcelable = parcelable,
    dealState = deal_state?.let { stateIndex ->
        DealState.values()[stateIndex]
    },
)

@Unsupported
internal fun FeedScoreData.toDomain() = FeedScore(
    userId = user_id.unwrap(
        field = "userId",
    ),
    feedId = feed_id.unwrap(
        field = "feedId",
    ),
    stayTime = stay_time.unwrap(
        field = "stayTime",
    ),
    score = score.unwrap(
        field = "score",
    ),
)

internal fun FollowData.toDomain() = Follow(
    userId = account_id.unwrap(
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
    targetId = target_id.unwrap(
        field = "feedId",
    ),
    userId = user_id.unwrap(
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
    nickname = nick_name.unwrap(
        field = "nickname",
    ),
    accountAvailable = account_enabled.unwrap(
        field = "accountAvailable",
    ),
    profileUrl = profile_url,
    tier = tier,
    badges = badges.unwrap(
        field = "badges",
    ).map { badgeIndex ->
        Badge.values()[badgeIndex]
    },
    likeCategories = like_categories.unwrap(
        field = "likeCategories",
    ).map { categoryIndex ->
        Category.values()[categoryIndex]
    },
    interestedTags = interested_tags.unwrap(
        field = "interestedTags",
    ),
    nonInterestedTags = non_interested_tags.unwrap(
        field = "nonInterestedTags",
    ),
    notificationTags = notification_tags.unwrap(
        field = "notificationTags",
    ),
    tradePreferenceTags = trade_preference_tags.unwrap(
        field = "tradePreferenceTags",
    ),
    collections = collections.unwrap(
        field = "collections",
    ),
    createdAt = create_at.unwrap(
        field = "createdAt",
    ).toDate(),
    deletedAt = delete_at?.toDate(),
    bannedAt = banned_at?.toDate(),
)

internal fun SettingData.toDomain() = SettingEntity(
    activityNotification = activityNotification,
    messageNotification = messageNotification,
)

internal fun AccountInformationData.toDomain() = team.duckie.app.android.domain.model.AccountInformationEntity(
    accountType = accountType,
    email = email,
)

internal fun LikeCategoryData.toDomain() = LikeCategory(
    id = id,
    title = title,
    imageUrl = imageUrl,
    popularTags = popularTags.toList(),
)

internal fun KakaoLoginResponseData.toDoMain() = KakaoLoginResponse(
    accessToken = token.accessToken,
    accessTokenExpiresAt = token.accessTokenExpiresAt,
    refreshToken = token.refreshToken,
    refreshTokenExpiresAt = token.refreshTokenExpiresAt,
    idToken = token.idToken,
    scopes = token.scopes,
)

internal fun LoginResponseData.toDomain() = LoginResponse(
    user = user.toDomain(),
)

internal fun LoginUserData.toDomain() = LoginUser(
    username = username,
)

internal fun SignUpResponseData.toDomain() = SignUpResponse(
    isSuccess = isSuccess,
)

internal fun NotificationData.toDomain() = when (type) {
    NotificationType.NewComment.key -> NotificationItem.NewComment(
        id = id,
        profileUrl = profileUrl,
        createdAt = createdAt,
        description = description,
        targetUserId = targetUserId,
        feedId = feedId,
    )

    NotificationType.NewHeart.key -> NotificationItem.NewHeart(
        id = id,
        profileUrl = profileUrl,
        createdAt = createdAt,
        description = description,
        targetUserId = targetUserId,
        feedId = feedId,
    )

    NotificationType.NewFollower.key -> NotificationItem.NewFollower(
        id = id,
        profileUrl = profileUrl,
        createdAt = createdAt,
        targetUserId = targetUserId,
        isFollowed = isFollowed,
    )

    NotificationType.RequireWriteReview.key -> NotificationItem.RequireWriteReview(
        id = id,
        profileUrl = profileUrl,
        createdAt = createdAt,
        duckDealTitle = duckDealTitle,
        duckDealUrl = duckDealUrl,
    )

    NotificationType.RequireChangeDealState.key -> NotificationItem.RequireChangeDealState(
        id = id,
        profileUrl = profileUrl,
        createdAt = createdAt,
        duckDealTitle = duckDealTitle,
        duckDealUrl = duckDealUrl,
    )

    else -> NotificationItem.RequireUpToDuckDeal(
        id = id,
        profileUrl = profileUrl,
        createdAt = createdAt,
        targetUserId = targetUserId,
        duckDealUrl = duckDealUrl,
    )
}
