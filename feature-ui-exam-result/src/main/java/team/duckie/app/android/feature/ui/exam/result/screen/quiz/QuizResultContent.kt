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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.exam.result.R
import team.duckie.app.android.shared.ui.compose.QuackAnnotatedText
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.util.kotlin.toHourMinuteSecond
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackImage
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
                    correctProblemCount.toString()
                )
            )
        }
        Spacer(space = 28.dp)
        QuackAnnotatedText(
            text = stringResource(id = R.string.rank_by_tag, mainTag, rank.toString()),
            highlightTextPairs = persistentListOf(
                mainTag to null,
                rank.toString() to null,
            ),
            style = QuackTextStyle.HeadLine1,
        )
        Spacer(space = 38.dp)
    }
}
