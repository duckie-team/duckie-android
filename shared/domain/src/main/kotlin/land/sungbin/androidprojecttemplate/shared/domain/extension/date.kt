@file:Suppress("HasPlatformType")

package land.sungbin.androidprojecttemplate.shared.domain.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.format(format: String, locale: Locale = Locale.KOREA) =
    SimpleDateFormat(format, locale).format(this)

fun Date.toCalendar() = Calendar.getInstance().apply { time = this@toCalendar }
