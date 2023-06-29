/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.solve.problem.question.audio.AudioPlayer
import team.duckie.app.android.feature.solve.problem.question.image.ImageBox
import team.duckie.app.android.feature.solve.problem.question.video.VideoPlayer
import team.duckie.quackquack.ui.component.QuackHeadLine2

private val HorizontalPadding = PaddingValues(horizontal = 16.dp)

@Composable
internal fun ColumnScope.QuestionSection(
    page: Int,
    question: Question,
    isImageChoice: Boolean,
) {
    val modifier = if (isImageChoice) {
        Modifier
            .fillMaxWidth()
            .aspectRatio(GetHeightRatioW328H240)
    } else {
        Modifier.weight(GetHeightRatioW328H240)
    }
    QuackHeadLine2(
        text = "${page + 1}. ${question.text}",
        padding = HorizontalPadding,
    )
    when (question) {
        is Question.Text -> {}
        is Question.Image -> {
            Spacer(space = 16.dp)
            ImageBox(
                modifier = modifier,
                url = question.imageUrl,
            )
        }

        is Question.Audio -> {
            AudioPlayer(
                modifier = Modifier.padding(paddingValues = HorizontalPadding),
                url = question.audioUrl,
            )
        }

        is Question.Video -> {
            VideoPlayer(url = question.videoUrl)
        }
    }
}
