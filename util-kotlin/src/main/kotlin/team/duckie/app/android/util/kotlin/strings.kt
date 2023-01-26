/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.kotlin

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
