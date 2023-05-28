/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.DuckieFitImage
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.solve.problem.question.audio.AudioPlayer
import team.duckie.app.android.feature.solve.problem.question.video.VideoPlayer
import team.duckie.quackquack.ui.component.QuackHeadLine2

private val HorizontalPadding = PaddingValues(horizontal = 16.dp)
internal fun LazyListScope.questionSection(
    page: Int,
    question: Question,
) {
    item {
        QuackHeadLine2(
            text = "${page + 1}. ${question.text}",
            padding = HorizontalPadding,
        )
    }
    when (question) {
        is Question.Text -> {}
        is Question.Image -> {
            item {
                DuckieFitImage(
                    imageUrl = question.imageUrl,
                )
            }
        }

        is Question.Audio -> {
            item {
                AudioPlayer(
                    modifier = Modifier.padding(paddingValues = HorizontalPadding),
                    url = question.audioUrl,
                )
            }
        }

        is Question.Video -> {
            item {
                VideoPlayer(url = question.videoUrl)
            }
        }
    }
}
