/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalDesignToken::class,
    ExperimentalQuackQuackApi::class,
    ExperimentalDesignToken::class,
)

package team.duckie.app.android.feature.exam.result.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import team.duckie.app.android.common.compose.DuckieFitImage
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v2.FilledHeart
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.screen.RANKER_THRESHOLD
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState.Success.ChallengeCommentUiModel
import team.duckie.quackquack.animation.animateQuackColorAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowDown
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackFilledTextField
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import java.util.Locale

@Composable
internal fun QuizResultContent(
    modifier: Modifier = Modifier,
    nickname: String,
    resultImageUrl: String,
    time: Double,
    correctProblemCount: Int,
    mainTag: String,
    message: String,
    ranking: Int,
    // for wrong answer
    profileImg: String,
    myAnswer: String,
    equalAnswerCount: Int,
    myComment: String,
    myCommentChanged: (String) -> Unit,
    onHeartComment: (Int) -> Unit,
    initState: suspend CoroutineScope.() -> Unit,
    comments: ImmutableList<ChallengeCommentUiModel>,
    commentsTotal: Int,
) {
    LaunchedEffect(key1 = Unit) {
        initState()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        if (myAnswer.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .background(
                        color = QuackColor.Gray4.value,
                        shape = RoundedCornerShape(8.dp),
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                QuackText(
                    text = "나의 답",
                    typography = QuackTypography.Body3.change(
                        color = QuackColor.Gray1,
                    ),
                )
                Spacer(space = 2.dp)
                QuackHeadLine2(text = myAnswer)
            }
            Spacer(space = 12.dp)
        }
        DuckieFitImage(
            imageUrl = resultImageUrl,
            horizontalPadding = PaddingValues(horizontal = 0.dp),
        )
        Spacer(space = 16.dp)
        QuackQuote(text = message)
        Spacer(space = 24.dp)
        QuackMaxWidthDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(79.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                QuizResultTitleAndDescription(
                    title = stringResource(
                        id = R.string.exam_result_time,
                        String.format(Locale.US, "%.2f", time),
                    ),
                    description = stringResource(id = R.string.exam_result_total_time),
                )
            }
            Box(
                modifier = Modifier
                    .size(1.dp, 18.dp)
                    .background(color = QuackColor.Gray3.value),
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                QuizResultTitleAndDescription(
                    title = stringResource(
                        id = R.string.exam_result_correct_problem_unit,
                        correctProblemCount,
                    ),
                    description = stringResource(id = R.string.exam_result_score),
                )
            }
        }
        QuackMaxWidthDivider()
        Spacer(space = 24.dp)
        QuackHeadLine1(
            modifier = Modifier.span(
                texts = listOf(nickname, mainTag, "${ranking}위"),
                style = SpanStyle(
                    color = QuackColor.DuckieOrange.value,
                    fontWeight = FontWeight.Bold,
                ),
            ),
            text = stringResource(
                id = if (ranking <= RANKER_THRESHOLD) {
                    R.string.exam_result_finish_title_ranker
                } else {
                    R.string.exam_result_finish_title_etc
                },
                nickname,
                mainTag,
                ranking,
            ),
        )
        // 오답 영역
        Spacer(space = 28.dp)
        QuizResultLargeDivider()
        PopularWrongAnswerSection(
            myAnswer = myAnswer,
            equalAnswerCount = equalAnswerCount,
        )
        QuizResultLargeDivider()
        WrongAnswerSection(
            profileUrl = profileImg,
            myAnswer = myAnswer,
            comment = myComment,
            onCommentChange = myCommentChanged,
            onHeartComment = onHeartComment,
            comments = comments,
            commentsTotal = commentsTotal,
        )
    }
}

@Composable
private fun ColumnScope.WrongAnswerSection(
    profileUrl: String,
    myAnswer: String,
    comment: String,
    onCommentChange: (String) -> Unit,
    onHeartComment: (Int) -> Unit,
    commentsTotal: Int,
    comments: ImmutableList<ChallengeCommentUiModel>,
) {
    Spacer(space = 28.dp)
    QuackHeadLine2(text = "답도 없는 오답들")
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
            QuackBody2(text = "나의 답")
            QuackHeadLine2(text = myAnswer)
        }
    }
    Spacer(space = 12.dp)
    QuackFilledTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        value = comment,
        onValueChange = onCommentChange,
        style = QuackTextFieldStyle.FilledLarge,
        placeholderText = "댓글을 남겨보세요!",
    )
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
            text = "전체 댓글 ${commentsTotal}개",
            typography = QuackTypography.Body1.change(
                color = QuackColor.Gray1,
            ),
        )
        QuackIcon(icon = QuackIcon.Outlined.ArrowDown)
    }
    Spacer(space = 8.dp)
    comments.forEach { item ->
        key(item.id) {
            WrongCommentInternal(
                wrongComment = item,
                onHeartClick = { commentId ->
                    onHeartComment(commentId)
                },
            )
            Spacer(space = 8.dp)
        }
    }
}

@Composable
private fun WrongCommentInternal(
    wrongComment: ChallengeCommentUiModel,
    onHeartClick: (Int) -> Unit,
) {
    val animateHeartColor =
        animateQuackColorAsState(targetValue = if (wrongComment.isHeart) QuackColor.Gray1 else QuackColor.Gray2)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Row {
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
}

@Composable
private fun ColumnScope.PopularWrongAnswerSection(
    myAnswer: String,
    equalAnswerCount: Int,
) {
    Spacer(space = 28.dp)
    QuackHeadLine2(text = "이 문제 인기 오답")
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
        QuackHeadLine2(text = myAnswer)
        Spacer(space = 4.dp)
        QuackBody2(text = "이 문제를 푼 유저 중 ${equalAnswerCount}명이 이렇게 답을 적었어요!")
    }
    Spacer(space = 28.dp)
}

@Composable
private fun QuizResultLargeDivider() {
    QuackMaxWidthDivider(
        modifier = Modifier.height(8.dp),
    )
}

@Composable
private fun QuizResultTitleAndDescription(
    title: String,
    description: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackHeadLine2(text = title)
        Spacer(space = 2.dp)
        QuackText(
            text = description,
            typography = QuackTypography.Body2.change(
                color = QuackColor.Gray1,
            ),
        )
    }
}

@OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)
@Composable
@Preview
fun PreviewTextField() {
    val text = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        QuackFilledTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            style = QuackTextFieldStyle.FilledLarge,
        )
        QuackFilledTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            style = QuackTextFieldStyle.FilledFlat,
        )
//        QuackFilledTextField(
//            value = text.value,
//            onValueChange = {
//                text.value = it
//            },
//            style = QuackTextFieldStyle.Default,
//        )
    }
}
