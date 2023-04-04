/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.section

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.shared.ui.compose.DuckExamSmallCover
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun ExamSection(
    @DrawableRes icon: Int,
    title: String,
    exams: ImmutableList<Exam>,
    onClickShowAll: () -> Unit,
    onClickExam: (Exam) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QuackImage(
                    src = icon,
                    size = DpSize(all = 24.dp)
                )
                QuackTitle2(text = title)
            }
            QuackBody2(
                text = "전체보기",
                color = QuackColor.Gray1,
                onClick = onClickShowAll,
            )
        }
        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(exams) { item ->
                DuckExamSmallCover(
                    duckTestCoverItem = DuckTestCoverItem(
                        testId = item.id,
                        thumbnailUrl = item.thumbnailUrl,
                        nickname = item.user?.nickname ?: "",
                        title = item.title,
                        solvedCount = item.solvedCount ?: 0,
                        heartCount = item.heartCount ?: 0,
                    ),
                    onItemClick = { onClickExam(item) },
                    onMoreClick = { }
                )
            }
        }
    }
}
