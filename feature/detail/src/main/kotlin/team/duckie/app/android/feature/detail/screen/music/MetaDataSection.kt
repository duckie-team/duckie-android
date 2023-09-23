/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.screen.music

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.kotlin.addAmountString
import team.duckie.app.android.common.kotlin.addCountString
import team.duckie.app.android.feature.detail.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun MusicMetadataSection(
    modifier: Modifier = Modifier,
    totalExam: Int,
    totalSolveCount: Int,
) {
    Column(
        modifier = modifier,
    ) {
        QuackTitle2(text = stringResource(id = R.string.detail_exam_info))
        Spacer(space = 12.dp)
        TitleAndInfo(
            title = stringResource(id = R.string.detail_music_total_exam),
            info = totalExam.toString().addAmountString(),
        )
        Spacer(space = 8.dp)
        TitleAndInfo(
            title = stringResource(id = R.string.detail_music_total_solve_count),
            info = totalSolveCount.toString().addCountString(),
        )
    }
}

@Composable
private fun TitleAndInfo(
    title: String,
    info: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackText(
            text = title,
            typography = QuackTypography.Body1.change(
                color = QuackColor.Gray1,
            ),
        )
        QuackText(
            text = info,
            typography = QuackTypography.Subtitle
        )
    }
}
