package land.sungbin.androidprojecttemplate.home.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import land.sungbin.androidprojecttemplate.R
import java.text.NumberFormat
import java.util.Locale

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


@Composable
internal fun Int.priceToString(): String =
    stringResource(
        id = R.string.price_with_won,
        NumberFormat.getInstance(Locale.getDefault()).format(this)
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