/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.constants

import team.duckie.app.android.common.kotlin.AllowMagicNumber

/**
 * Home 의 Step 을 관리하는 enum class
 *
 * @param index HomeStep 의 idnex
 */
@AllowMagicNumber
internal enum class HomeStep(
    val index: Int,
) {
    HomeRecommendScreen(
        index = 0,
    ),

    HomeFollowingScreen(
        index = 1,
    ),

    // TODO(riflockle7): 추후 index = 1
    HomeProceedScreen(
        index = 2,
    ),
    ;

    companion object {
        fun toStep(value: Int) = HomeStep.values().first { it.index == value }
    }
}
