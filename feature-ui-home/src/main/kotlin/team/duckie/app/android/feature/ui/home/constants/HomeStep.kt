package team.duckie.app.android.feature.ui.home.constants

import team.duckie.app.android.util.kotlin.AllowMagicNumber

/**
 * Home 의 Step 을 관리하는 enum class
 *
 * @param index HomeStep 의 idnex
 */
@AllowMagicNumber
enum class HomeStep(
    val index: Int,
) {
    HomeRecommendScreen(
        index = 0,
    ),

    HomeFollowingScreen(
        index = 1,
    ),

    HomeTagScreen(
        index = 2,
    );

    companion object {
        fun toStep(value: Int) = HomeStep.values().first { it.index == value }
    }
}
