/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.exam.result.screen.wronganswer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.DraggableBox
import team.duckie.app.android.common.compose.PullToDeleteButton
import team.duckie.app.android.common.compose.WrapScaffoldLayout
import team.duckie.app.android.common.compose.rememberToast
import team.duckie.app.android.common.compose.ui.QuackIconWrapper
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v1.ArrowSendId
import team.duckie.app.android.common.compose.ui.icon.v2.FilledHeart
import team.duckie.app.android.common.compose.ui.icon.v2.Order18
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.common.compose.util.fillMaxScreenWidth
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.quackquack.animation.animateQuackColorAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Delete
import team.duckie.quackquack.material.icon.quackicon.outlined.Flag
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun ChallengeCommentBottomSheetContent(
    modifier: Modifier = Modifier,
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
    fullScreen: Boolean,
    onDeleteComment: (Int) -> Unit,
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
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
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
                        placeholderText = "댓글을 남겨보세요!",
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

private const val ComingSoon = "Comming Soon!"

@Composable
internal fun DraggableChallengeComment(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues,
    wrongComment: ExamResultState.Success.ChallengeCommentUiModel,
    onHeartClick: (Int) -> Unit,
    isMine: Boolean,
    onDeleteComment: (() -> Unit)? = null,
) {
    val (isRevealed, onChange) = remember { mutableStateOf(false) }
    val toast = rememberToast()

    DraggableBox(
        modifier = modifier,
        isRevealed = isRevealed,
        onRevealedChanged = { onChange(it) },
        backgroundContent = { modifier ->
            Row {
                if(isMine) {
                    PullToDeleteButton(
                        modifier = modifier,
                        icon = QuackIcon.Outlined.Delete,
                        onClick = { onDeleteComment?.invoke() },
                    )
                } else {
                    PullToDeleteButton(
                        modifier = modifier,
                        icon = QuackIcon.Outlined.Flag,
                        onClick = { toast.invoke(ComingSoon) },
                    )
                    // TODO(limsaehyun) 신고 및 차단 기능 이곳에 구현하기
                }
            }
        },
        content = { modifier ->
            ChallengeComment(
                modifier = modifier,
                wrongComment = wrongComment,
                onHeartClick = onHeartClick,
                innerPaddingValues = innerPaddingValues,
            )
        },
    )
}

@Composable
private fun ChallengeComment(
    modifier: Modifier = Modifier,
    wrongComment: ExamResultState.Success.ChallengeCommentUiModel,
    innerPaddingValues: PaddingValues = PaddingValues(),
    onHeartClick: (Int) -> Unit,
) {
    val animateHeartColor =
        animateQuackColorAsState(targetValue = if (wrongComment.isHeart) QuackColor.Gray1 else QuackColor.Gray2)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(QuackColor.White.value)
            .padding(innerPaddingValues)
            .padding(vertical = 8.dp),
    ) {
        QuackProfileImage(
            profileUrl = wrongComment.userProfileImg,
            size = DpSize(width = 40.dp, height = 40.dp),
        )
        Spacer(space = 8.dp)
        Column {
            QuackText(
                text = "${wrongComment.userNickname}님의 답",
                typography = QuackTypography.Body3.change(
                    color = QuackColor.Gray1,
                ),
            )
            Spacer(space = 2.dp)
            QuackTitle2(text = wrongComment.wrongAnswer)
            Spacer(space = 6.dp)
            QuackBody1(text = wrongComment.message)
            Spacer(space = 6.dp)
            QuackText(
                text = wrongComment.createdAt,
                typography = QuackTypography.Body3.change(
                    color = QuackColor.Gray2,
                ),
            )
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
