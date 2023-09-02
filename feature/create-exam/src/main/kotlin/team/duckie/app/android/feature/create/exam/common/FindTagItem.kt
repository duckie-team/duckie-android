/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackBody1

@Composable
internal fun SearchResultText(
    text: String,
    onClick: () -> Unit,
) = QuackBody1(
    modifier = Modifier.fillMaxWidth(),
    padding = PaddingValues(vertical = 12.dp),
    text = text,
    onClick = onClick,
)
