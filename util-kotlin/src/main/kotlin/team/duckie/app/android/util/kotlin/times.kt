/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

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
