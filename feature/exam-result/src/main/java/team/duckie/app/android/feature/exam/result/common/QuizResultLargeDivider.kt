/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.common

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.quackquack.material.QuackColor

@Composable
internal fun QuizResultLargeDivider() {
    QuackMaxWidthDivider(
        modifier = Modifier.height(8.dp),
        color = QuackColor.Gray4,
    )
}
