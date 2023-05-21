/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:Suppress("MagicNumber")

package team.duckie.app.android.common.kotlin

import java.util.Date

fun Date.getDiffDayFromToday(): String {
    val currentTime = Date()
    val differenceInMillis = currentTime.time - this.time

    val seconds = differenceInMillis / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val months = days / 30
    val years = months / 12

    return when {
        years > 0 -> "${years}년 전"
        months > 0 -> "${months}달 전"
        days > 0 -> "${days}일 전"
        hours > 0 -> "${hours}시간 전"
        minutes > 0 -> "${minutes}분 전"
        else -> "${seconds}초 전"
    }
}
