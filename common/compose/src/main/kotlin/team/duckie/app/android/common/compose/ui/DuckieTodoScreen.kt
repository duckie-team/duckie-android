/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.ui.icon.v1.TextLogoId
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText

/** Duckie 추후 개발 Screen */
@Composable
fun DuckieTodoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuackColor.White.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        QuackImage(
            src = QuackIcon.TextLogoId,
            modifier = Modifier.size(72.dp, 24.dp),
        )

        QuackText(
            modifier = Modifier.padding(top = 16.dp),
            typography = QuackTypography.Title1.change(textAlign = TextAlign.Center),
            text = stringResource(id = R.string.todo_title),
        )
    }
}
