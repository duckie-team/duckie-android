/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.start.exam.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.start.exam.R
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamState
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamViewModel
import team.duckie.app.android.common.compose.ui.ImeSpacer
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackGrayscaleTextField
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
internal fun StartQuizInputScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    val state =
        viewModel.container.stateFlow.collectAsStateWithLifecycle().value as StartExamState.Input

    val certifyingStatement: String = remember(state.certifyingStatement) {
        state.certifyingStatement
    }
    val certifyingStatementText: String = remember(state.certifyingStatementInputText) {
        state.certifyingStatementInputText
    }

    Column(modifier = modifier) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onLeadingIconClick = viewModel::finishStartExam,
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            InfoBox(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                limitTime = 10,
            )
            QuackTitle2(
                modifier = Modifier.padding(top = 34.dp),
                text = stringResource(id = R.string.challenge_condition),
            )
            Spacer(space = 4.dp)
            QuackHeadLine1(
                modifier = Modifier.padding(top = 4.dp),
                text = state.certifyingStatement, // TODO(EvergreenTree97) 추후 도전 조건 response 생기면 변경
            )
            QuackGrayscaleTextField(
                modifier = Modifier.padding(top = 14.dp),
                text = certifyingStatementText,
                onTextChanged = viewModel::inputCertifyingStatement,
                placeholderText = "ex) $certifyingStatement",
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        QuackLargeButton(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
            type = QuackLargeButtonType.Fill,
            text = stringResource(id = R.string.start_button),
            enabled = viewModel.startExamValidate(),
            onClick = viewModel::startSolveProblem,
        )
        ImeSpacer()
    }
}

@Composable
internal fun InfoBox(
    modifier: Modifier = Modifier,
    limitTime: Int,
) {
    Column(
        modifier = modifier
            .background(color = QuackColor.Gray4.composeColor)
            .padding(all = 12.dp)
            .clip(RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Center,
    ) {
        QuackTitle2(text = stringResource(id = R.string.information_before_quiz_title))
        Spacer(space = 4.dp)
        QuackBody2(text = stringResource(id = R.string.information_before_quiz_line1))
        QuackText(
            annotatedText = buildAnnotatedString {
                append(stringResource(id = R.string.information_before_quiz_line2_prefix))
                withStyle(
                    SpanStyle(
                        color = QuackColor.Black.composeColor,
                        fontWeight = FontWeight.Bold,
                    ),
                ) {
                    append(
                        stringResource(
                            id = R.string.information_before_quiz_line2_infix,
                            limitTime.toString(),
                        ),
                    )
                }
                append(stringResource(id = R.string.information_before_quiz_line2_postfix))
            },
            style = QuackTextStyle.Body2,
        )
    }
}
