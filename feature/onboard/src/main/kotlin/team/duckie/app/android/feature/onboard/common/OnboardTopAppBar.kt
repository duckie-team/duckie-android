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
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.compose.ui.quack.todo.QuackTopAppBar
import team.duckie.app.android.feature.onboard.constant.OnboardStep
import team.duckie.app.android.feature.onboard.viewmodel.OnboardViewModel
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowBack

@Composable
internal fun OnboardTopAppBar(
    modifier: Modifier = Modifier,
    currentStep: OnboardStep,
    vm: OnboardViewModel = activityViewModel(),
) {
    QuackTopAppBar(
        modifier = modifier.padding(top = systemBarPaddings.calculateTopPadding()),
        leadingIcon = QuackIcon.Outlined.ArrowBack,
        onLeadingIconClick = { vm.navigateStep(currentStep - 1) },
    )
}
