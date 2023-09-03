/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.feature.create.exam.R
import team.duckie.app.android.feature.create.exam.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.create.exam.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.create.exam.viewmodel.state.CreateProblemStep

/** 단일 문제 만들기 Screen */
@Composable
internal fun CreateProblemScreen(
    modifier: Modifier,
    vm: CreateProblemViewModel = activityViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        PrevAndNextTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            onLeadingIconClick = {
                coroutineScope.launch { vm.navigateStep(CreateProblemStep.ExamInformation) }
            },
            trailingText = stringResource(id = R.string.next),
            onTrailingTextClick = {},
            trailingTextEnabled = true,
        )
    }
}
