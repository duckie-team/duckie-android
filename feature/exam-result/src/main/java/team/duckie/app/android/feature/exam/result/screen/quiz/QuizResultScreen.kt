/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalDesignToken::class,
    ExperimentalQuackQuackApi::class,
    ExperimentalDesignToken::class, ExperimentalMaterialApi::class,
)

package team.duckie.app.android.feature.exam.result.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import team.duckie.app.android.common.compose.DuckieFitImage
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.common.QuizResultLargeDivider
import team.duckie.app.android.feature.exam.result.screen.RANKER_THRESHOLD
import team.duckie.app.android.feature.exam.result.screen.wronganswer.ChallengeCommentSection
import team.duckie.app.android.feature.exam.result.screen.wronganswer.PopularCommentSection
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState.Success.ChallengeCommentUiModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackFilledTextField
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.sugar.QuackQuote
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import java.util.Locale

@Composable
internal fun QuizResultScreen(
    modifier: Modifier = Modifier,
    initState: suspend CoroutineScope.() -> Unit,
    // default
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
    onMyCommentChanged: (String) -> Unit,
    onHeartComment: (Int) -> Unit,
    comments: ImmutableList<ChallengeCommentUiModel>,
    commentsTotal: Int,
    showCommentSheet: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        initState()
    }

    QuizResultContent(
        modifier = modifier,
        nickname = nickname,
        resultImageUrl = resultImageUrl,
        time = time,
        correctProblemCount = correctProblemCount,
        mainTag = mainTag,
        message = message,
        ranking = ranking,
        profileImg = profileImg,
        myAnswer = myAnswer,
        equalAnswerCount = equalAnswerCount,
        myComment = myComment,
        myCommentChanged = onMyCommentChanged,
        onHeartComment = onHeartComment,
        comments = comments,
        commentsTotal = commentsTotal,
        showCommentSheet = showCommentSheet,
    )
}

@Composable
private fun QuizResultContent(
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
    comments: ImmutableList<ChallengeCommentUiModel>,
    commentsTotal: Int,
    showCommentSheet: () -> Unit,
) {
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
        PopularCommentSection(
            myAnswer = myAnswer,
            equalAnswerCount = equalAnswerCount,
        )
        QuizResultLargeDivider()
        ChallengeCommentSection(
            profileUrl = profileImg,
            myAnswer = myAnswer,
            onHeartComment = onHeartComment,
            comments = comments,
            commentsTotal = commentsTotal,
            showCommentSheet = showCommentSheet,
        )
    }
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
