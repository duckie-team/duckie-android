/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.solve.problem.question.audio.AudioPlayer
import team.duckie.app.android.feature.solve.problem.question.image.FlexibleImageBox
import team.duckie.app.android.feature.solve.problem.question.image.ImageBox
import team.duckie.app.android.feature.solve.problem.question.video.VideoPlayer
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

private val HorizontalPadding = 16.dp

@Composable
internal fun ColumnScope.QuestionSection(
    page: Int,
    question: Question,
    isImageChoice: Boolean,
    isRequireFlexibleImage: Boolean,
    spaceImageToKeyboard: Dp,
    onImageLoading: (Boolean) -> Unit,
    onImageSuccess: (Boolean) -> Unit,
    isImageLoading: Boolean,
) {
    val modifier = if (isImageChoice) {
        Modifier
            .fillMaxWidth()
            .aspectRatio(GetHeightRatioW328H240)
    } else {
        Modifier.weight(GetHeightRatioW328H240)
    }
    QuackHeadLine2(
        modifier = Modifier.padding(horizontal = HorizontalPadding),
        text = "${page + 1}. ${question.text}",
    )
    when (question) {
        is Question.Text -> {}
        is Question.Image -> {
            Spacer(space = 12.dp)
            if (isRequireFlexibleImage) {
                FlexibleImageBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    spaceImageToKeyboard = spaceImageToKeyboard,
                    url = question.imageUrl,
                    onImageLoading = onImageLoading,
                    onImageSuccess = onImageSuccess,
                    isImageLoading = isImageLoading,
                )
            } else {
                ImageBox(
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .background(
                            color = QuackColor.Gray4.value,
                            shape = RoundedCornerShape(8.dp),
                        ),
                    url = question.imageUrl,
                    onImageLoading = onImageLoading,
                    onImageSuccess = onImageSuccess,
                    isImageLoading = isImageLoading,
                )
            }
        }

        is Question.Audio -> {
            AudioPlayer(
                modifier = Modifier.padding(horizontal = HorizontalPadding),
                url = question.audioUrl,
            )
        }

        is Question.Video -> {
            VideoPlayer(url = question.videoUrl)
        }
    }
}
