/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.kotlin

fun Int.isFirstRanked() =
    this == 1

private const val TOP_RANK_CUT_LINE = 10

fun Int.isTopRanked() =
    this <= TOP_RANK_CUT_LINE
