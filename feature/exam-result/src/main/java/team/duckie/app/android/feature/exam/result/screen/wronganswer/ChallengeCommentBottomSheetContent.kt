/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen.wronganswer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.WrapScaffoldLayout
import team.duckie.app.android.common.compose.ui.QuackIconWrapper
import team.duckie.app.android.common.compose.ui.icon.v1.ArrowSendId
import team.duckie.app.android.common.compose.ui.icon.v2.Order18
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.common.compose.util.fillMaxScreenWidth
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.component.QuackDivider

@Composable
internal fun ChallengeCommentBottomSheetContent(
    modifier: Modifier = Modifier,
    totalComments: Int,
    orderType: CommentOrderType,
    onOrderTypeChanged: () -> Unit,
    myComment: String,
    myCommentCreateAt: String,
    isWriteComment: Boolean,
    onMyCommentChanged: (String) -> Unit,
    comments: ImmutableList<ExamResultState.Success.ChallengeCommentUiModel>,
    onHeartComment: (Int) -> Unit,
    onSendComment: () -> Unit,
    fullScreen: Boolean,
    onDeleteComment: (Int) -> Unit,
    onIgnoreComment: (Int, String) -> Unit,
    onReportComment: (Int) -> Unit,
) {
    WrapScaffoldLayout(
        fullScreen = fullScreen,
        modifier = modifier
            .imePadding(),
        topBar = {
            if (comments.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    QuackText(
                        text = stringResource(
                            id = R.string.exam_result_total_comment,
                            totalComments
                        ),
                        typography = QuackTypography.Title2.change(
                            color = QuackColor.Gray1,
                        ),
                    )
                    QuackIconWrapper(
                        icon = QuackIcon.Order18,
                        onClick = onOrderTypeChanged,
                        size = 18.dp,
                    ) {
                        QuackText(
                            modifier = Modifier.padding(start = 2.dp),
                            text = orderType.kor,
                            typography = QuackTypography.Body2.change(
                                color = QuackColor.Gray1,
                            ),
                        )
                    }
                }
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(comments) { item ->
                    DraggableChallengeComment(
                        modifier = Modifier.fillMaxScreenWidth(),
                        innerPaddingValues = PaddingValues(horizontal = 16.dp),
                        wrongComment = item,
                        onHeartClick = { commentId ->
                            onHeartComment(commentId)
                        },
                        isMine = item.isMine,
                        onDeleteComment = { onDeleteComment(item.id) },
                        onIgnoreComment = { onIgnoreComment(item.userId, item.userNickname) },
                        onReportComment = { onReportComment(item.id) },
                        visibleHeart = true,
                    )
                }
            }
        },
        bottomBar = {
            if (isWriteComment) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(
                            color = QuackColor.Gray4.value
                        )
                        .padding(all = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    QuackText(
                        text = myComment,
                        typography = QuackTypography.Body1.change(
                            color = QuackColor.Gray1
                        ),
                    )
                    QuackText(
                        text = myCommentCreateAt,
                        typography = QuackTypography.Body3.change(
                            color = QuackColor.Gray2,
                        ),
                    )
                }
            } else {
                Column {
                    QuackDivider()
                    QuackNoUnderlineTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(QuackColor.Gray4.value),
                        text = myComment,
                        onTextChanged = onMyCommentChanged,
                        placeholderText = stringResource(id = R.string.exam_result_input_comment_hint),
                        trailingIcon = QuackIcon.ArrowSendId,
                        trailingIconOnClick = onSendComment,
                        paddingValues = PaddingValues(
                            vertical = 14.dp,
                            horizontal = 16.dp,
                        )
                    )
                }
            }
        },
    )
}
