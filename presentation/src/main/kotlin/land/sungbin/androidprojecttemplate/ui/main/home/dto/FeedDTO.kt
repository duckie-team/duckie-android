package land.sungbin.androidprojecttemplate.ui.main.home.dto

import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import java.util.Date

sealed class FeedDTO(
    open val feedId: String,
    open val writerId: String,
    open val type: FeedType,
    open val content: Content,
    open val createdAt: Date,
    open val isHearted: Boolean,
    open val heartCount: Int,
    open val commentCount: Int,
) {
    data class Normal(
        override val feedId: String,
        override val writerId: String,
        override val type: FeedType,
        override val content: Content,
        override val createdAt: Date,
        override val isHearted: Boolean,
        override val heartCount: Int,
        override val commentCount: Int,
    ) : FeedDTO(feedId, writerId, type, content, createdAt, isHearted, heartCount, commentCount)

    data class DuckDeal(
        override val feedId: String,
        override val writerId: String,
        override val type: FeedType,
        override val content: Content,
        override val createdAt: Date,
        override val isHearted: Boolean,
        override val heartCount: Int,
        override val commentCount: Int,
        val title: String,
        val dealState: DealState,
        val price: Int,
        val location: String,
        val isDirectDealing: Boolean,
        val parcelable: Boolean,
    ) : FeedDTO(feedId, writerId, type, content, createdAt, isHearted, heartCount, commentCount)
}

fun Feed.toNormalFeed() = FeedDTO.Normal(
    feedId = id,
    writerId = writerId,
    type = type,
    content = content,
    createdAt = createdAt,
    isHearted = false, //TODO
    heartCount = 0,
    commentCount = 0,
)

fun Feed.toDuckDealFeed() = FeedDTO.DuckDeal(
    feedId = id,
    writerId = writerId,
    type = type,
    content = content,
    createdAt = createdAt,
    isHearted = false, //TODO
    heartCount = 0,
    commentCount = 0,
    title = title!!,
    dealState = dealState!!,
    price = price!!,
    location = location!!,
    isDirectDealing = isDirectDealing!!,
    parcelable = parcelable!!,
)
