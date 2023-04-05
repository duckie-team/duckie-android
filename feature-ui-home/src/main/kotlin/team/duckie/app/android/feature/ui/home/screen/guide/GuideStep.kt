/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.guide

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.util.kotlin.AllowMagicNumber

@AllowMagicNumber
internal enum class GuideStep(
    val index: Int,
    @StringRes
    val subtitle: Int,
    @StringRes
    val title: Int,
    @DrawableRes
    val image: Int,
) {
    HOME(
        index = 0,
        subtitle = R.string.guide_home_subtitle,
        title = R.string.guide_home_title,
        image = R.drawable.bg_guide_home,
    ),
    DETAIL(
        index = 1,
        subtitle = R.string.guide_detail_subtitle,
        title = R.string.guide_detail_title,
        image = R.drawable.bg_guide_detail,
    ),
    CREATE(
        index = 2,
        subtitle = R.string.guide_create_subtitle,
        title = R.string.guide_create_title,
        image = R.drawable.bg_guide_create,
    ),
    ;

    companion object {
        fun getGuideStepByIndex(index: Int): GuideStep {
            return GuideStep.values().first { it.index == index }
        }
    }
}
