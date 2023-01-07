/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 2022-11-28T06:37:39.915Z
private const val BackendDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"

internal fun String.toDate(): Date? {
    val formatter = SimpleDateFormat(BackendDateFormat, Locale.KOREA)
    return formatter.parse(this)
}
