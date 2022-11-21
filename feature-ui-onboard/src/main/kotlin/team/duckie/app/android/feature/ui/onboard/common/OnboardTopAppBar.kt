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
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun OnboardTopAppBar() {
    val vm = LocalViewModel.current as OnboardViewModel

    // TODO: center text 에 v 아이콘 표시 여부 인자로 받게 변경
    // TODO: center content 인자들 assertion 선택적으로 변경
    // TODO: trailing content 인자들 assertion 선택적으로 변경
    QuackTopAppBar(
        modifier = Modifier.padding(horizontal = 12.dp), // 내부에서 8.dp 가 들어감
        leadingIcon = QuackIcon.ArrowBack,
        onLeadingIconClick = {
            vm.updateStep(vm.currentStep - 1)
        },
        centerText = "",
        trailingText = "",
    )
}
