/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:AllowMagicNumber

package team.duckie.app.android.feature.detail.screen.funding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.feature.detail.common.DetailContentLayout
import team.duckie.app.android.feature.detail.common.DetailProfileSection
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState

@Composable
internal fun FundingDetailContentLayout(
    modifier: Modifier = Modifier,
    state: DetailState.Success,
    tagItemClick: (String) -> Unit,
    moreButtonClick: () -> Unit,
    followButtonClick: () -> Unit,
    profileClick: (Int) -> Unit,
) {
    DetailContentLayout(
        modifier = modifier,
        state = state,
        tagItemClick = tagItemClick,
        moreButtonClick = moreButtonClick,
        followButtonClick = followButtonClick,
        profileClick = profileClick,
        additionalInfo = {
            DetailProfileSection(state, followButtonClick, profileClick)

            FundingSection()
        },
    )
}

@Composable
fun FundingSection() {

}
