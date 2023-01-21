/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTitle1

/** Duckie 추후 개발 Screen */
@Composable
fun DuckieTodoScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        QuackImage(
            src = team.duckie.quackquack.ui.R.drawable.quack_duckie_text_logo,
            size = DpSize(width = 72.dp, height = 24.dp),
        )

        QuackTitle1(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.todo_title),
            align = TextAlign.Center,
        )
    }
}
