/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.CoverImageRatio
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.solve.problem.question.audio.AudioPlayer
import team.duckie.app.android.feature.solve.problem.question.video.VideoPlayer
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackImage
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = QuackColor.Black.value,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .aspectRatio(CoverImageRatio),
                    contentAlignment = Alignment.Center,
                ) {
                    QuackImage(
                        modifier = Modifier.fillMaxSize(),
                        src = question.imageUrl,
                        contentScale = ContentScale.Fit,
                    )
                }
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
