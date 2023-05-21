/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.onboard.common

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.onboard.constant.OnboardStep
import team.duckie.app.android.feature.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun OnboardTopAppBar(
    modifier: Modifier = Modifier,
    currentStep: OnboardStep,
    horizontalPadding: Dp = 20.dp,
    vm: OnboardViewModel = activityViewModel(),
) {
    QuackTopAppBar(
        modifier = modifier
            .padding(top = systemBarPaddings.calculateTopPadding())
            .padding(horizontal = horizontalPadding - 8.dp), // 내부에서 8.dp 가 들어감
        leadingIcon = QuackIcon.ArrowBack,
        onLeadingIconClick = { vm.navigateStep(currentStep - 1) },
    )
}
