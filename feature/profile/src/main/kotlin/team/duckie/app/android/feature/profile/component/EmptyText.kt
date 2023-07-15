/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.sugar.QuackBody1

@Composable
internal fun EmptyText(message: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        QuackBody1(
            modifier = Modifier.padding(vertical = 20.dp),
            text = message,
        )
    }
}
