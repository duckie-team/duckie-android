/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.util.kotlin

/**
 * 아직 개발중인 API 라 변경이 있을 수 있음을 나타냅니다.
 */
@RequiresOptIn(
    message = "This API is experimental and may change in the future.",
    level = RequiresOptIn.Level.WARNING,
)
annotation class ExperimentalApi

/**
 * 현재 구성이 변경된 API 를 나타냅니다. 최신 구성에 맞게 변경이 필요합니다.
 */
@RequiresOptIn(message = "This API is out-of-date. Please update this code.")
annotation class OutOfDateApi
