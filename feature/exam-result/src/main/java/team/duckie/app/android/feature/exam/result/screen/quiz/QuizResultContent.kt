/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.common.kotlin.toHourMinuteSecond
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun QuizResultContent(
    resultImageUrl: String,
    time: Int,
    correctProblemCount: Int,
    mainTag: String,
    rank: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
    ) {
        QuackImage(
            modifier = Modifier.fillMaxWidth(),
            src = resultImageUrl,
        )
        // TODO(EvergreenTree97) : 시험 결과지 스펙에 맞게 수정 및 문구 출력
        Spacer(space = 28.dp)
        QuackDivider()
        Spacer(space = 20.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackBody1(text = stringResource(id = R.string.total_time))
            QuackBody1(text = time.toHourMinuteSecond())
        }
        Spacer(space = 8.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackBody1(text = stringResource(id = R.string.correct_problem))
            QuackBody1(
                text = stringResource(
                    id = R.string.correct_problem_unit,
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
                    append("${rank}위")
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
