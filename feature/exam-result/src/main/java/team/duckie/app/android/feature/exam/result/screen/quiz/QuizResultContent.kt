/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
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
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import java.util.Locale

@Composable
internal fun QuizResultContent(
    modifier: Modifier = Modifier,
    initialState: suspend CoroutineScope.() -> Unit,
    nickname: String,
    resultImageUrl: String,
    time: Double,
    correctProblemCount: Int,
    mainTag: String,
    message: String,
    ranking: Int,
    // challenge comment
    profileImg: String,
    myAnswer: String,
    equalAnswerCount: Int,
    onHeartComment: (Int) -> Unit,
    comments: ImmutableList<ExamResultState.Success.ChallengeCommentUiModel>,
    commentsTotal: Int,
    showCommentSheet: () -> Unit,
    onDeleteComment: (Int) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        initialState()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        DuckieFitImage(
            imageUrl = resultImageUrl,
            horizontalPadding = PaddingValues(horizontal = 0.dp),
        )
        Spacer(space = 16.dp)
        // TODO(limsaehyun): QuackText Quote의 버그가 픽스된 후 아래 코드로 변경해야 함
        // https://duckie-team.slack.com/archives/C054HU0CKMY/p1688278156256779
        // QuackText(text = message, typography = QuackTypography.Quote)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            QuackHeadLine1(text = "\"")
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                QuackHeadLine1(text = message)
            }
            QuackHeadLine1(text = "\"")
        }
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
            onDeleteComment = onDeleteComment,
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
