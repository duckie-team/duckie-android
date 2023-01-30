/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.start.exam.R
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamState
import team.duckie.app.android.feature.ui.start.exam.viewmodel.StartExamViewModel
import team.duckie.app.android.shared.ui.compose.ImeSpacer
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackGrayscaleTextField
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackTitle1
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun StartExamScreen(
    modifier: Modifier,
    viewModel: StartExamViewModel = activityViewModel(),
) = when (viewModel.collectAsState().value) {
    is StartExamState.Loading -> StartExamLoadingScreen(modifier, viewModel)
    is StartExamState.Input -> StartExamInputScreen(modifier, viewModel)
    is StartExamState.Error -> StartExamErrorScreen(modifier, viewModel)
}

/**
 * 시험 시작 Loading 화면
 * @see [StartExamState.Loading]
 */
@Composable
private fun StartExamLoadingScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    LaunchedEffect(Unit) {
        viewModel.initState()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO(riflockle7): 추후 DuckieCircularProgressIndicator.kt 와 합치거나 꽥꽥 컴포넌트로 필요
        CircularProgressIndicator(
            color = QuackColor.DuckieOrange.composeColor,
        )
    }
}

/**
 * 시험 시작 입력 화면
 * @see [StartExamState.Input]
 */
@Composable
internal fun StartExamInputScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    val state = viewModel.collectAsState().value as StartExamState.Input

    val certifyingStatement: String = remember(state.certifyingStatement) {
        state.certifyingStatement
    }
    val certifyingStatementText: String = remember(state.certifyingStatementInputText) {
        state.certifyingStatementInputText
    }

    Column(modifier = modifier) {
        // 상단 탭바
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onLeadingIconClick = viewModel::finishStartExam,
        )

        // 제목
        QuackHeadLine1(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            text = stringResource(id = R.string.title),
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
        QuackLargeButton(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
            type = QuackLargeButtonType.Fill,
            text = stringResource(id = R.string.start_button),
            enabled = viewModel.startExamValidate(),
            onClick = viewModel::finishStartExam,
        )

        ImeSpacer()
    }
}

/**
 * 시험 시작 에러 화면
 * @see [StartExamState.Error]
 */
@Composable
private fun StartExamErrorScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO(riflockle7): 추후 DuckieCircularProgressIndicator.kt 와 합치거나 꽥꽥 컴포넌트로 필요
        QuackTitle1(
            text = "에러입니다\nTODO$viewModel\n추후 데이터 다시 가져오기 로직 넣어야 합니다.",
        )
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
            .background(color = QuackColor.Gray4.composeColor)
            .padding(
                vertical = 17.dp,
                horizontal = 20.dp,
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = QuackTextStyle.Body1.asComposeStyle(),
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
                        style = QuackTextStyle.Body1.change(color = QuackColor.Gray2),
                    )
                }
            }

            Box(propagateMinConstraints = true) {
                textField()
            }
        },
    )
}
