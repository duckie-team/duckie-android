/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.DuckExamSmallCover
import team.duckie.app.android.common.compose.ui.DuckTestCoverItem
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.feature.profile.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
fun ExamSection(
    isLoading: Boolean,
    icon: ImageVector,
    title: String,
    exams: ImmutableList<DuckTestCoverItem>,
    onClickExam: (DuckTestCoverItem) -> Unit,
    onClickMore: (() -> Unit)? = null,
    onClickShowAll: () -> Unit,
    emptySection: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                QuackIcon(
                    modifier = Modifier
                        .size(DpSize(24.dp, 24.dp))
                        .skeleton(isLoading),
                    icon = icon,
                )
                QuackTitle2(
                    modifier = Modifier.skeleton(isLoading),
                    text = title,
                )
            }
            if (exams.isNotEmpty()) {
                QuackText(
                    modifier = Modifier.quackClickable(onClick = onClickShowAll),
                    text = stringResource(id = R.string.profile_view_all),
                    typography = QuackTypography.Body2.change(color = QuackColor.Gray1),
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
                item{
                    Spacer(space = 16.dp)
                }
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
