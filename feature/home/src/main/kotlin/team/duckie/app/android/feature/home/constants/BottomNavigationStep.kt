/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.constants

import team.duckie.app.android.common.kotlin.AllowMagicNumber

/**
 * BototmNavigation의 Step을 관리하는 enum class
 *
 * @param index step의 index
 */
@AllowMagicNumber
internal enum class BottomNavigationStep(
    val index: Int,
) {
    HomeScreen(index = 0),
    MusicScreen(index = 1),
    SearchScreen(index = 2),
    RankingScreen(index = 3),
    MyPageScreen(index = 4),
    ;

    companion object {
        fun toStep(value: Int) = BottomNavigationStep.values().first { it.index == value }
    }
}
