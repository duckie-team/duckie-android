/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.constans

enum class SearchResultStep(
    val title: String,
    val index: Int,
) {
    DUCK_TEST(
        title = "덕질고사",
        index = 0,
    ),
    USER(
        title = "사용자",
        index = 1,
    );

    companion object {
        fun toStep(value: Int) = SearchResultStep.values().first { it.index == value }
    }
}
