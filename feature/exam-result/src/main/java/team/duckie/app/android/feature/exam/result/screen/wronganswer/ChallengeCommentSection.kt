/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen.wronganswer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.common.compose.util.fillMaxScreenWidth
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowDown
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

@Composable
internal fun ColumnScope.ChallengeCommentSection(
    profileUrl: String,
    myAnswer: String,
    onHeartComment: (Int) -> Unit,
    commentsTotal: Int,
    comments: ImmutableList<ExamResultState.Success.ChallengeCommentUiModel>,
    showCommentSheet: () -> Unit,
    onDeleteComment: (Int) -> Unit,
    onIgnoreComment: (Int, String) -> Unit,
    onReportComment: (Int) -> Unit,
) {
    Spacer(space = 28.dp)
    QuackHeadLine2(text = stringResource(id = R.string.exam_result_wrong_comment_title))
    Spacer(space = 20.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackProfileImage(
            profileUrl = profileUrl,
            size = DpSize(width = 40.dp, height = 40.dp),
        )
        Spacer(space = 8.dp)
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            QuackBody2(text = stringResource(id = R.string.my_answer))
            QuackHeadLine2(text = myAnswer)
        }
    }
    Spacer(space = 12.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(QuackColor.Gray4.value)
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
                shape = RoundedCornerShape(8.dp),
            )
            .quackClickable(
                onClick = showCommentSheet,
            )
            .padding(
                horizontal = 12.dp,
                vertical = 16.dp,
            ),
    ) {
        QuackText(
            text = stringResource(id = R.string.exam_result_input_comment_hint),
            typography = QuackTypography.Body2.change(
                color = QuackColor.Gray2,
            ),
        )
    }
    Spacer(space = 20.dp)
    QuackMaxWidthDivider()
    Spacer(space = 20.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackText(
            text = stringResource(id = R.string.exam_result_total_comment, commentsTotal),
            typography = QuackTypography.Body1.change(
                color = QuackColor.Gray1,
            ),
        )
        QuackIcon(icon = QuackIcon.Outlined.ArrowDown)
    }
    Spacer(space = 8.dp)
    comments.forEach { item ->
        key(item.id) {
            DraggableChallengeComment(
                modifier = Modifier.fillMaxScreenWidth(),
                wrongComment = item,
                onHeartClick = { commentId ->
                    onHeartComment(commentId)
                },
                isMine = item.isMine,
                innerPaddingValues = PaddingValues(horizontal = 16.dp),
                onDeleteComment = { onDeleteComment(item.id) },
                onIgnoreComment = { onIgnoreComment(item.userId, item.userNickname) },
                onReportComment = { onReportComment(item.id) },
                visibleHeart = true,
            )
            Spacer(space = 8.dp)
        }
    }
}

@Composable
internal fun ColumnScope.PopularCommentSection(
    total: Int,
    data: String,
) {
    Spacer(space = 28.dp)
    QuackHeadLine2(text = stringResource(id = R.string.exam_result_popular_comment))
    Spacer(space = 8.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFEFCF),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                vertical = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        QuackHeadLine2(text = data)
        Spacer(space = 4.dp)
        QuackBody2(
            text = stringResource(
                id = R.string.exam_result_equal_comment_value,
                total
            )
        )
    }
    Spacer(space = 28.dp)
}
