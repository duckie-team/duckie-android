/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.launch
import team.duckie.quackquack.ui.component.QuackTitle1

/** 문제 만들기 2단계 (문제 만들기) Screen */
@Composable
fun CreateProblemScreen(modifier: Modifier) = CoroutineScopeContent {
    val vm = LocalViewModel.current as CreateProblemViewModel

    Column(modifier = modifier) {
        // 상단 탭바
        PrevAndNextTopAppBar(
            onLeadingIconClick = {
                launch { vm.navigateStep(CreateProblemStep.ExamInformation) }
            },
            trailingText = stringResource(id = R.string.next),
            onTrailingTextClick = {
                launch { vm.navigateStep(CreateProblemStep.AdditionalInformation) }
            },
            trailingTextEnabled = vm.isAllFieldsNotEmpty(),
        )

        // 컨텐츠 Layout
        LazyColumn(
            content = {
                item { QuackTitle1(text = "문제 만들기 2단계 화면입니다.\n(flow 를 위해 임시로 생성함)") }
            }
        )
    }
}
