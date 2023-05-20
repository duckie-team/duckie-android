package team.duckie.app.android.feature.home.constants

import team.duckie.app.android.util.kotlin.AllowMagicNumber

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
    ;

    companion object {
        fun toStep(value: Int) = HomeStep.values().first { it.index == value }
    }
}
