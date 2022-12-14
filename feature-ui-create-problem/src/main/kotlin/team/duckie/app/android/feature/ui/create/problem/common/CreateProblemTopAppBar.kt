/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun CreateProblemTopAppBar(
    onLeadingIconClick: () -> Unit,
    onTrailingTextClick: () -> Unit,
    trailingTextEnabled: Boolean = true,
) {
    //TODO [EvergreenTree97] enabled 속성 필요
    QuackTopAppBar(
        leadingIcon = QuackIcon.ArrowBack,
        leadingText = stringResource(id = R.string.create_problem),
        onLeadingIconClick = onLeadingIconClick,
        trailingText = stringResource(id = R.string.create_problem_next),
        onTrailingTextClick = when (trailingTextEnabled) {
            true -> onTrailingTextClick
            else -> null
        }
    )
}

