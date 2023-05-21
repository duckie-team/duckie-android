/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.screen.exam

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackImage

@Composable
internal fun ExamResultContent(
    resultImageUrl: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        QuackImage(
            modifier = Modifier.fillMaxSize(),
            src = resultImageUrl,
        )
    }
}
