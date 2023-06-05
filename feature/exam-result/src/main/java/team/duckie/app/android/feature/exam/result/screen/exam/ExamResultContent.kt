/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen.exam

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import team.duckie.quackquack.ui.QuackImage

@Composable
internal fun ExamResultContent(
    resultImageUrl: String,
) {
    QuackImage(
        modifier = Modifier.fillMaxSize(),
        src = resultImageUrl,
        contentScale = ContentScale.FillBounds,
    )
}
