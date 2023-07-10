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
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import team.duckie.app.android.common.compose.DuckieFitImage
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.kotlin.isFirstRanked
import team.duckie.app.android.common.kotlin.isTopRanked
import team.duckie.app.android.common.kotlin.toHourMinuteSecond
import team.duckie.app.android.feature.exam.result.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

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
    reaction: String,
    onReactionChanged: (String) -> Unit,
) {
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
        QuackDivider()
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
                    title = time.toHourMinuteSecond(),
                    description = stringResource(id = R.string.exam_result_total_time),
                )
            }
            Box(
                modifier = Modifier
                    .size(1.dp, 18.dp)
                    .background(color = QuackColor.Gray3.value)
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
                    description = stringResource(id = R.string.exam_result_correct_problem),
                )
            }
        }
        QuackDivider()
        Spacer(space = 24.dp)

        QuackText(
            modifier = Modifier.span(
                texts = listOf("${nickname}님", mainTag, "${ranking}위"),
                style = SpanStyle(
                    color = QuackColor.DuckieOrange.value,
                    fontWeight = FontWeight.Bold,
                ),
            ),
            text = stringResource(
                id = R.string.exam_result_finish_title,
                nickname,
                mainTag,
                ranking
            ),
            typography = QuackTypography(
                size = 20.sp,
                weight = FontWeight.Normal,
                letterSpacing = (-0.01).sp,
                lineHeight = 26.sp,
            )
        )
        Spacer(space = 12.dp)
        if (ranking.isTopRanked()) {
            QuizResultReactionTextArea(
                reaction = reaction,
                onReactionChanged = onReactionChanged,
                maxLength = if (ranking.isFirstRanked()) 40 else 15,
            )
            Spacer(space = 22.dp)
        }
    }
}

@Composable
private fun QuizResultReactionTextArea(
    maxLength: Int,
    reaction: String,
    onReactionChanged: (String) -> Unit,
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        QuackReviewTextArea(
            modifier = Modifier.height(100.dp),
            text = reaction,
            onTextChanged = { str ->
                if (str.length <= maxLength) {
                    onReactionChanged(str)
                }
            },
            focused = false,
            placeholderText = stringResource(id = R.string.exam_result_first_ranked_text_area_hint),
        )
        QuackText(
            modifier = Modifier.padding(
                bottom = 12.dp,
                end = 12.dp,
            ),
            text = "${reaction.length} / $maxLength",
            typography = QuackTypography.Body1.change(
                color = QuackColor.Gray2
            ),
        )
    }
}

@Composable
private fun QuizResultTitleAndDescription(
    title: String,
    description: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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
