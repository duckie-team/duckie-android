/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.kotlin

import team.duckie.app.android.common.kotlin.exception.duckieClientLogicProblemException

/**
 * 주어진 숫자를 % 로 반환합니다. 각 숫자마다 규칙이 있습니다.
 * 소수 : 100 을 곱한 뒤 %
 * 기타 : 그냥 뒤에 % 붙이기
 */
inline val Number.percents: String
    get() = when {
        this.toInt() !in 0..100 -> duckieClientLogicProblemException("퍼센트는 0 ~ 100 범위로 표현되어야 합니다.")
        this is Float -> "${(this * 100).toInt()}%"
        this is Double -> "${(this * 100).toInt()}%"
        else -> "$this%"
    }

/**
 * 현재 문자열에서 [최대 허용 길이][maximumLength] 만큼의 문자열을 반환한다.
 * [최대 허용 길이][maximumLength] 를 넘을 시 [이전 값][prevValue]을 반환한다.
 */
fun String.takeBy(maximumLength: Int, prevValue: String): String {
    return if (this.length > maximumLength) {
        prevValue
    } else {
        this.take(maximumLength)
    }
}

fun randomString(length: Int): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZ"
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}

fun String?.orHyphen(): String =
    if(this.isNullOrEmpty()) "-" else this
