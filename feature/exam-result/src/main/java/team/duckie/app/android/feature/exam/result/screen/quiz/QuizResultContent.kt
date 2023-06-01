/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.DuckieFitImage
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.kotlin.toHourMinuteSecond
import team.duckie.app.android.feature.exam.result.R
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun QuizResultContent(
    resultImageUrl: String,
    time: Int,
    correctProblemCount: Int,
    mainTag: String,
    message: String,
    ranking: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        DuckieFitImage(
            imageUrl = resultImageUrl,
            horizontalPadding = PaddingValues(horizontal = 0.dp),
        )
        Spacer(space = 16.dp)
        // TODO(EvergreenTree97) : 에러 문구 폰트 변경 필요
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackHeadLine1(text = "\"")
            QuackHeadLine1(text = message)
            QuackHeadLine1(text = "\"")
        }
        Spacer(space = 28.dp)
        QuackDivider()
        Spacer(space = 20.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackBody1(text = stringResource(id = R.string.exam_result_total_time))
            QuackBody1(text = time.toHourMinuteSecond())
        }
        Spacer(space = 8.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackBody1(text = stringResource(id = R.string.exam_result_correct_problem))
            QuackBody1(
                text = stringResource(
                    id = R.string.exam_result_correct_problem_unit,
                    correctProblemCount.toString(),
                ),
            )
        }
        Spacer(space = 28.dp)
        QuackText(
            modifier = Modifier.align(Alignment.End),
            annotatedText = buildAnnotatedString {
                append("당신은 ")
                withDuckieOrangeBoldStyle {
                    append(mainTag)
                }
                append(" 영역 ")
                withDuckieOrangeBoldStyle {
                    append("${ranking}위")
                }
                append(" 입니다.")
            },
            style = QuackTextStyle.HeadLine1,
        )
        Spacer(space = 38.dp)
    }
}

private inline fun <R : Any> AnnotatedString.Builder.withDuckieOrangeBoldStyle(
    block: AnnotatedString.Builder.() -> R,
): R {
    withStyle(
        SpanStyle(
            color = QuackColor.DuckieOrange.composeColor,
            fontWeight = FontWeight.Bold,
        ),
    ) {
        return block()
    }
}
