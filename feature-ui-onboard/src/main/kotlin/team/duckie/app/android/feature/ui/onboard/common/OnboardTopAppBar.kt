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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.launch
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun OnboardTopAppBar(
    modifier: Modifier = Modifier,
    currentStep: OnboardStep,
    showSkipTrailingText: Boolean,
    horizontalPadding: Dp = 20.dp,
) = CoroutineScopeContent {
    val vm = LocalViewModel.current as OnboardViewModel
    val context = LocalContext.current
    val toast = rememberToast()

    QuackTopAppBar(
        modifier = modifier
            .padding(top = systemBarPaddings.calculateTopPadding())
            .padding(horizontal = horizontalPadding - 8.dp), // 내부에서 8.dp 가 들어감
        leadingIcon = QuackIcon.ArrowBack,
        onLeadingIconClick = {
            vm.navigateStep(currentStep - 1)
        },
        trailingText = stringResource(R.string.topappbar_trailing_skip).takeIf { showSkipTrailingText },
        onTrailingTextClick = {
            launch {
                context.dataStore.edit { preference ->
                    preference[PreferenceKey.Onboard.Finish] = true
                }
                toast("온보딩 끝")
            }
            Unit
        }.takeIf { showSkipTrailingText },
    )
}
