/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackBody1

@Composable
internal fun SearchResultText(
    text: String,
    onClick: () -> Unit,
) = QuackBody1(
    padding = PaddingValues(vertical = 12.dp),
    text = text,
    onClick = onClick,
)

