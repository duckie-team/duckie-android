/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.home.screen.music

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.RowMusicItem
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.rowSpacer
import team.duckie.app.android.common.compose.ui.toUiModel
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.feature.home.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackHeadLine1

@Composable
fun TitleAndMusicContents(
    modifier: Modifier = Modifier,
    title: String,
    onClickShowAll: () -> Unit,
    exams: ImmutableList<Exam>,
    onClickExam: (Exam) -> Unit,
    onClickMore: (Exam) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackHeadLine1(text = title)
            QuackText(
                modifier = Modifier.quackClickable(onClick = onClickShowAll),
                typography = QuackTypography.Subtitle.change(
                    color = QuackColor.Gray1,
                ),
                text = stringResource(id = R.string.home_music_view_all_button_title),
            )
        }
        Spacer(space = 12.dp)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            rowSpacer(4.dp)
            items(
                items = exams
            ) { item ->
                val uiModel = item.toUiModel()
                RowMusicItem(
                    exam = uiModel,
                    onClickExam = { onClickExam(item) },
                    onClickMore = { onClickMore(item) },
                )
            }
        }
    }
}
