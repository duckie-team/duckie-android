package land.sungbin.androidprojecttemplate.home.dto

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import java.text.NumberFormat
import java.util.Locale
@Immutable
open class NormalFeed(
    val id: String,
    val type: FeedType,
    val content: Content,
    val createdAt: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NormalFeed

        if (id != other.id) return false
        if (type != other.type) return false
        if (content != other.content) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}

@Immutable
class DuckDealFeed(
    id: String,
    type: FeedType,
    content: Content,
    createdAt: String,
    val title: String = "제목",
    val dealState: DealState = DealState.Booking,
    val price: String = "30,000 원",
    val dealMethodAndLocation: String = "택배, 직거래 - 마포구 도화동",
) : NormalFeed(id, type, content, createdAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as DuckDealFeed

        if (title != other.title) return false
        if (dealState != other.dealState) return false
        if (price != other.price) return false
        if (dealMethodAndLocation != other.dealMethodAndLocation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + dealState.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + dealMethodAndLocation.hashCode()
        return result
    }
}

fun Feed.toNormalFeed() = NormalFeed(
    id = id,
    type = type,
    content = content,
    createdAt = "",
)

fun Feed.toDuckDealFeed(context: Context) = DuckDealFeed(
    id = id,
    type = type,
    content = content,
    createdAt = "",
    title = title!!,
    dealState = dealState!!,
    price = priceToString(context = context)
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

internal fun Feed.priceToString(context: Context): String =
    context.getString(
        R.string.price_with_won,
        NumberFormat.getInstance(Locale.getDefault()).format(price)
    )

@Composable
internal fun getTradingMethodAndLocation(
    isDirectDealing: Boolean,
    parcelable: Boolean,
    location: String,
) = stringResource(
    id = R.string.center_period_between_text,
    stringResource(id = getTradingMethodResourceId(isDirectDealing, parcelable)),
    location
)


private fun getTradingMethodResourceId(isDirectDealing: Boolean, parcelable: Boolean): Int {
    return if (isDirectDealing && parcelable) {
        R.string.both_direct_dealing_parcelable
    } else if (isDirectDealing) {
        R.string.parcelable
    } else {
        R.string.direct_dealing
    }
}