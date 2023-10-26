/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.screen.music

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.detail.common.DetailContentLayout
import team.duckie.app.android.feature.detail.common.MetadataSection
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState

@Composable
internal fun MusicDetailContentLayout(
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
            Spacer(modifier = Modifier.padding(top = 10.dp))
            MetadataSection(
                totalExamCount = state.exam.problemCount ?: 0,
                totalExaminee = state.exam.solvedCount ?: 0,
            )
            Spacer(modifier = Modifier.padding(bottom = 32.dp))
            MusicRankingSection(
                modifier = Modifier.fillMaxSize(),
                state = state,
                userContentClick = profileClick,
            )
        },
    )
}
