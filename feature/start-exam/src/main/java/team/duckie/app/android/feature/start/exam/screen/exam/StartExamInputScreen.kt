/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.start.exam.screen.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.ui.BackPressedTopAppBar
import team.duckie.app.android.common.compose.ui.ImeSpacer
import team.duckie.app.android.feature.start.exam.R
import team.duckie.app.android.feature.start.exam.screen.StartExamScreen
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamState
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackPrimaryLargeButton
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/**
 * 시험 시작 입력 화면
 * @see [StartExamState.Input]
 */
@Composable
internal fun StartExamInputScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    val state =
        viewModel.container.stateFlow.collectAsStateWithLifecycle().value as StartExamState.Input

    val certifyingStatement: String = remember(state.certifyingStatement) {
        state.certifyingStatement
    }
    val certifyingStatementText: String = remember(state.certifyingStatementInputText) {
        state.certifyingStatementInputText
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // 상단 탭바
        BackPressedTopAppBar(onBackPressed = viewModel::finishStartExam)

        // 제목
        QuackHeadLine1(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            text = stringResource(id = R.string.start_exam_title),
        )

        // 필적 확인 문구 TextField
        StartExamTextField(
            modifier = Modifier.padding(
                top = 28.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            text = certifyingStatementText,
            onTextChanged = viewModel::inputCertifyingStatement,
            placeholderText = certifyingStatement,
        )

        // 여백
        Spacer(modifier = Modifier.weight(1f))

        // 시험시작 버튼
        QuackPrimaryLargeButton(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            text = stringResource(id = R.string.start_exam_start_button),
            onClick = viewModel::startSolveProblem,
            enabled = viewModel.startExamValidate(),
        )
        ImeSpacer()
    }
}

/**
 * [시험 시작 화면][StartExamScreen]에서 활용하는 TextField
 * 기존 [QuackGrayscaleTextField] 와 차이가 있다면, placeholder 가 계속 유지된다.
 *
 * // TODO(riflockle7): 추후 QuackQuack 라이브러리에서 해당 기능 제공 시 아래 코드 제거 및 대체 필요
 */
@Composable
internal fun StartExamTextField(
    modifier: Modifier = Modifier,
    text: String,
    alwaysPlaceholderVisible: Boolean = true,
    placeholderText: String,
    onTextChanged: (text: String) -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(color = QuackColor.Gray4.value)
            .padding(
                vertical = 17.dp,
                horizontal = 20.dp,
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = QuackTypography.Body1.asComposeStyle(),
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = keyboardActions,
        singleLine = true,
        cursorBrush = QuackColor.Black.toBrush(),
        decorationBox = { textField ->
            Box(propagateMinConstraints = true) {
                // TODO(riflockle7): 추후 QuackTextFieldCommonBody1Placeholder 내용에 반영 필요
                if (alwaysPlaceholderVisible || text.isEmpty()) {
                    QuackText(
                        text = placeholderText,
                        typography = QuackTypography.Body1.change(color = QuackColor.Gray2),
                    )
                }
            }

            Box(propagateMinConstraints = true) {
                textField()
            }
        },
    )
}
