/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.section

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.DuckExamSmallCover
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.feature.profile.R
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.util.DpSize

@Composable
fun ExamSection(
    isLoading: Boolean,
    @DrawableRes icon: Int,
    title: String,
    exams: ImmutableList<DuckTestCoverItem>,
    onClickExam: (DuckTestCoverItem) -> Unit,
    onClickMore: (() -> Unit)? = null,
    onClickShowAll: () -> Unit,
    emptySection: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.skeleton(isLoading),
                    src = icon,
                    size = DpSize(all = 24.dp),
                )
                QuackTitle2(
                    modifier = Modifier.skeleton(isLoading),
                    text = title,
                )
            }
            if(exams.isNotEmpty()){
                QuackBody2(
                    text = stringResource(id = R.string.profile_view_all),
                    color = QuackColor.Gray1,
                    onClick = onClickShowAll,
                )
            }
        }
        Spacer(space = 16.dp)
        if (exams.isEmpty()) {
            emptySection()
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(exams) { item ->
                    DuckExamSmallCover(
                        isLoading = isLoading,
                        duckTestCoverItem = DuckTestCoverItem(
                            testId = item.testId,
                            thumbnailUrl = item.thumbnailUrl,
                            nickname = item.nickname,
                            title = item.title,
                            solvedCount = item.solvedCount,
                            heartCount = item.heartCount,
                        ),
                        onItemClick = { onClickExam(item) },
                        onMoreClick = onClickMore,
                    )
                }
            }
        }
    }
}
