/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.common

import androidx.compose.runtime.Composable
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun CreateProblemTopAppBar(
    onLeadingIconClick: () -> Unit,
    onTrailingTextClick: () -> Unit,
) {
    QuackTopAppBar(
        leadingIcon = QuackIcon.ArrowBack,
        leadingText = "문제 만들기",
        onLeadingIconClick = onLeadingIconClick,
        trailingText = "다음",
        onTrailingTextClick = onTrailingTextClick
    )
}

