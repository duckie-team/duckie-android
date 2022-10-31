package land.sungbin.androidprojecttemplate.util

import java.text.NumberFormat
import java.util.Locale

object IntUtil {
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
}
