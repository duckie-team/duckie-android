/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.common

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun OnboardTopAppBar(
    modifier: Modifier = Modifier,
    showSkipTrailingText: Boolean,
    horizontalPadding: Dp = 20.dp,
) {
    val vm = LocalViewModel.current as OnboardViewModel

    QuackTopAppBar(
        modifier = modifier
            .padding(top = systemBarPaddings.calculateTopPadding())
            .padding(horizontal = horizontalPadding - 8.dp), // 내부에서 8.dp 가 들어감
        leadingIcon = QuackIcon.ArrowBack,
        onLeadingIconClick = {
            vm.updateStep(vm.currentStep - 1)
        },
        trailingText = stringResource(R.string.topappbar_trailing_skip)
            .takeIf { showSkipTrailingText },
        onTrailingTextClick = {
            vm.updateStep(vm.currentStep + 1)
        }.takeIf { showSkipTrailingText },
    )
}
