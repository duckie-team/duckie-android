/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:Suppress("MagicNumber")

package team.duckie.app.android.util.kotlin

/**
 * 주어진 초를 MS 로 반환합니다.
 */
inline val Int.seconds: Long get() = this * 1000L

/**
 * 주어진 초를 MS 로 반환합니다.
 */
inline val Double.seconds: Long get() = (this * 1000).toLong()

/**
 * 주어진 분을 MS 로 반환합니다.
 */
inline val Int.minutes: Long get() = this.seconds * 60

/**
 * 주어진 초를 HH:MM:SS 형식 으로 변환합니다.
 */
fun Int.toHourMinuteSecond(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}
