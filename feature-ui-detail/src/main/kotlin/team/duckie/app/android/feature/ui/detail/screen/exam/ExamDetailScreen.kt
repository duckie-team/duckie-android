/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:AllowMagicNumber

package team.duckie.app.android.feature.ui.detail.screen.exam

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.app.android.feature.ui.detail.common.DetailContentLayout
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
import team.duckie.app.android.util.kotlin.AllowMagicNumber

@Composable
internal fun ExamDetailContentLayout(
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
            // TODO(EvergreenTree97) 시험 정보 추후 삽입
        }
    )
}

