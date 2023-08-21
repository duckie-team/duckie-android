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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.ImeSpacer
import team.duckie.app.android.common.compose.ui.QuackIconWrapper
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v1.ArrowSendId
import team.duckie.app.android.common.compose.ui.icon.v2.FilledHeart
import team.duckie.app.android.common.compose.ui.icon.v2.Order18
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.quackquack.animation.animateQuackColorAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun ChallengeCommentBottomSheetContent(
    commentsTotal: Int,
    orderType: CommentOrderType,
    onOrderTypeChanged: () -> Unit,
    myComment: String,
    myCommentCreateAt: String,
    isWriteComment: Boolean,
    onMyCommentChanged: (String) -> Unit,
    comments: ImmutableList<ExamResultState.Success.ChallengeCommentUiModel>,
    onHeartComment: (Int) -> Unit,
    onSendComment: () -> Unit,
) {
    Scaffold(
        topBar = {
            if(comments.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    QuackText(
                        text = "전체 댓글 ${commentsTotal}개",
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
        bottomBar = {
            if(isWriteComment) {
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
                            .height(52.dp)
                            .background(QuackColor.Gray4.value),
                        text = myComment,
                        onTextChanged = onMyCommentChanged,
                        placeholderText = "댓글을 남겨보세요!",
                        trailingEndPadding = 16.dp,
                        trailingIcon = QuackIcon.ArrowSendId,
                        trailingIconOnClick = onSendComment,
                    )
                }
            }
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 8.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(comments) { item ->
                    ChallengeComment(
                        wrongComment = item,
                        onHeartClick = { commentId ->
                            onHeartComment(commentId)
                        },
                    )
                }
            }
        }
    )
    ImeSpacer()
}


@Composable
internal fun ChallengeComment(
    wrongComment: ExamResultState.Success.ChallengeCommentUiModel,
    onHeartClick: (Int) -> Unit,
) {
    val animateHeartColor =
        animateQuackColorAsState(targetValue = if (wrongComment.isHeart) QuackColor.Gray1 else QuackColor.Gray2)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        QuackProfileImage(
            profileUrl = wrongComment.user.profileImageUrl,
            size = DpSize(width = 40.dp, height = 40.dp),
        )
        Spacer(space = 8.dp)
        Column {
            Row {
                QuackText(
                    text = "${wrongComment.user.nickname}님의 답",
                    typography = QuackTypography.Body3.change(
                        color = QuackColor.Gray1,
                    ),
                )
                Spacer(space = 4.dp)
                QuackText(
                    text = wrongComment.createdAt,
                    typography = QuackTypography.Body3.change(
                        color = QuackColor.Gray2,
                    ),
                )
            }
            Spacer(space = 2.dp)
            QuackTitle2(text = wrongComment.wrongAnswer)
            Spacer(space = 4.dp)
            QuackBody1(text = wrongComment.message)
        }
        Spacer(weight = 1f)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuackIcon(
                modifier = Modifier
                    .size(24.dp)
                    .quackClickable(
                        onClick = {
                            onHeartClick(wrongComment.id)
                        },
                    ),
                icon = if (wrongComment.isHeart) {
                    QuackIcon.FilledHeart
                } else {
                    QuackIcon.Outlined.Heart
                },
            )
            QuackText(
                text = wrongComment.heartCount.toString(),
                typography = QuackTypography.Body3.change(
                    color = animateHeartColor.value,
                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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
                text = "낚시 오지네ㅜㅋㅋ",
                typography = QuackTypography.Body1.change(
                    color = QuackColor.Gray1
                ),
            )
            QuackText(
                text = "방금 전",
                typography = QuackTypography.Body3.change(
                    color = QuackColor.Gray2,
                ),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackText(
                text = "전체 댓글 ${3}개",
                typography = QuackTypography.Title2.change(
                    color = QuackColor.Gray1,
                ),
            )
            QuackIconWrapper(
                icon = QuackIcon.Order18,
                onClick = { },
            ) {
                QuackText(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "최신훈",
                    typography = QuackTypography.Body2.change(
                        color = QuackColor.Gray1,
                    ),
                )
            }
        }
    }
}
