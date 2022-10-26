package land.sungbin.androidprojecttemplate.ui.home.dto

import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import java.text.NumberFormat
import java.util.Locale

sealed class FeedDTO(
    open val feedId: String,
    open val writerId: String,
    open val type: FeedType,
    open val content: Content,
    open val createdAt: String,
    open val isHearted: Boolean,
    open val heartCount: Int,
    open val commentCount: Int,
) {
    data class Normal(
        override val feedId: String,
        override val writerId: String,
        override val type: FeedType,
        override val content: Content,
        override val createdAt: String,
        override val isHearted: Boolean,
        override val heartCount: Int,
        override val commentCount: Int,
    ) : FeedDTO(feedId, writerId, type, content, createdAt, isHearted, heartCount, commentCount)

    data class DuckDeal(
        override val feedId: String,
        override val writerId: String,
        override val type: FeedType,
        override val content: Content,
        override val createdAt: String,
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
    createdAt = "",
    isHearted = isHearted,
    heartCount = heartCount,
    commentCount = commentCount,
)

fun Feed.toDuckDealFeed() = FeedDTO.DuckDeal(
    feedId = id,
    writerId = writerId,
    type = type,
    content = content,
    createdAt = "",
    isHearted = isHearted,
    heartCount = heartCount,
    commentCount = commentCount,
    title = title!!,
    dealState = dealState!!,
    price = price!!,
    location = location!!,
    isDirectDealing = isDirectDealing!!,
    parcelable = parcelable!!,
)

private const val K = 1000
private const val M = 1000 * 1000

internal fun Int.toUnitString(): String =
    when (this) {
        in 0 until K -> {
            toString()
        }

        in K until M -> {
            (this / K).toString() + "k"
        }

        else -> {
            (this / M).toString() + "m"
        }
    }

internal fun Int.priceToString(): String =
    NumberFormat.getInstance(Locale.getDefault()).format(this)