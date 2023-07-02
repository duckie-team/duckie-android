/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.search.constants

internal enum class SearchResultStep(
    val title: String,
    val index: Int,
) {
    DuckExam(
        title = "덕력고사",
        index = 0,
    ),
    User(
        title = "사용자",
        index = 1,
    ),
    ;

    companion object {
        fun toStep(value: Int) = SearchResultStep.values().first { it.index == value }
    }
}
