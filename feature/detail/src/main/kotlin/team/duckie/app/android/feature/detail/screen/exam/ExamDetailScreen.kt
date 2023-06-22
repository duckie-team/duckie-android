/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:AllowMagicNumber

package team.duckie.app.android.feature.detail.screen.exam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.icon.v2.Paper
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.common.DetailContentLayout
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackTitle2

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
            MetadataSection(
                totalExamCount = state.exam.problemCount ?: 0,
                totalExaminee = state.exam.solvedCount ?: 0,
            )
        },
    )
}

@Composable
private fun MetadataSection(
    totalExamCount: Int,
    totalExaminee: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        QuackTitle2(text = stringResource(id = R.string.detail_exam_info))
        IconText(
            icon = QuackIcon.Paper,
            text = stringResource(id = R.string.detail_total_exam_count, totalExamCount),
        )
        IconText(
            icon = QuackIcon.Profile,
            text = stringResource(id = R.string.detail_total_examinee, totalExaminee),
        )
    }
}

@Composable
private fun IconText(
    modifier: Modifier = Modifier,
    icon: QuackIcon,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        QuackIcon(icon = icon)
        QuackBody2(text = text)
    }
}
