/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.exam.result.screen.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.shared.ui.compose.QuackAnnotatedText
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.util.kotlin.toHourMinuteSecond
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun QuizResultContent(
    resultImageUrl: String,
    correctProblemCount: Int,
    score: Int,
    time: Int,
    mainTag: String,
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
        Spacer(space = 48.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackBody1(text = "총 응시시간")
            QuackBody1(text = correctProblemCount.toString()+"덕")
        }
        Spacer(space = 8.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackBody1(text = "맞은 문제")
            QuackBody1(text = score.toHourMinuteSecond())
        }
        Spacer(space = 28.dp)
        QuackAnnotatedText(
            text = "당신은 로맨스 영역 10위 입니다.",
            highlightTextPairs = persistentListOf(
                "로맨스" to null,
                "10위" to null,
            ),
            style = QuackTextStyle.HeadLine1,
        )
    }
}
