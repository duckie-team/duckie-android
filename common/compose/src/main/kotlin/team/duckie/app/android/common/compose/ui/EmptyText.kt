/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

@Composable
fun NoItemScreen(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackText(
            modifier = Modifier.skeleton(isLoading),
            typography = QuackTypography.HeadLine1.change(color = QuackColor.Gray1),
            text = title,
        )

        QuackText(
            modifier = Modifier.skeleton(isLoading),
            typography = QuackTypography.Body2.change(color = QuackColor.Gray1),
            text = description,
        )
    }
}
