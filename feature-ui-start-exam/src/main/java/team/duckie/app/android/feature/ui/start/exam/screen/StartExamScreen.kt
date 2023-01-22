/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.start.exam.R
import team.duckie.app.android.shared.ui.compose.ImeSpacer
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackGrayscaleTextField
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
fun StartExamScreen() {
    val activity = LocalContext.current as Activity

    // TODO(riflockle7): 추후 ViewModel 연동 시 다른 값으로 바꾸어야 함
    val certifyingStatement: String by remember {
        mutableStateOf(activity.getString(R.string.certifing_statement_placeholder))
    }
    // TODO(riflockle7): 추후 ViewModel 연동 시 val 로 바꾸어야 함
    var certifyingStatementText: String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuackColor.White.composeColor)
            .systemBarsPadding()
    ) {
        // 상단 탭바
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onLeadingIconClick = { activity.finish() }
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
            onTextChanged = { certifyingStatementText = it },
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
            enabled = certifyingStatement == certifyingStatementText
        ) {
            Log.i("riflockle7", "시험 시작 클릭")
        }

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
