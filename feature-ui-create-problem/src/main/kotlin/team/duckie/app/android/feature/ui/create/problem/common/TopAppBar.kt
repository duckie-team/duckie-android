/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun PrevAndNextTopAppBar(
    onLeadingIconClick: () -> Unit,
    onTrailingTextClick: () -> Unit,
    trailingTextEnabled: Boolean = true,
) { // TODO(EvergreenTree97) enabled 속성 필요
    QuackTopAppBar(
        leadingIcon = QuackIcon.ArrowBack,
        leadingText = stringResource(id = R.string.create_problem),
        onLeadingIconClick = onLeadingIconClick,
        trailingText = stringResource(id = R.string.next),
        onTrailingTextClick = when (trailingTextEnabled) {
            true -> onTrailingTextClick
            else -> null
        },
    )
}
// TODO [EvergreenTree] QuackTopAppBar에 leadingIcon이 nullable로 변경됨이 필요
@Composable
internal fun ExitAppBar(
    modifier: Modifier = Modifier,
    leadingText: String,
    onTrailingIconClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackHeadLine2(text = leadingText)
        QuackImage(
            src = QuackIcon.Close,
            onClick = onTrailingIconClick,
        )
    }
}
