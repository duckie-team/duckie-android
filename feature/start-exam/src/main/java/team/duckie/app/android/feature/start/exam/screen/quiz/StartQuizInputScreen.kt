/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.start.exam.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.ui.BackPressedTopAppBar
import team.duckie.app.android.common.compose.ui.ImeSpacer
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.temp.TempFlexiblePrimaryLargeButton
import team.duckie.app.android.feature.start.exam.R
import team.duckie.app.android.feature.start.exam.screen.exam.StartExamTextField
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamState
import team.duckie.app.android.feature.start.exam.viewmodel.StartExamViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@Composable
internal fun StartQuizInputScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    val state =
        viewModel.container.stateFlow.collectAsStateWithLifecycle().value as StartExamState.Input

    val certifyingStatementText: String = remember(state.certifyingStatementInputText) {
        state.certifyingStatementInputText
    }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(modifier = modifier.fillMaxWidth()) {
        BackPressedTopAppBar(onBackPressed = viewModel::finishStartExam)
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            Spacer(space = 12.dp)
            InfoBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = QuackColor.Gray4.value)
                    .padding(all = 12.dp),
                limitTime = state.timer,
            )
            QuackTitle2(
                modifier = Modifier.padding(top = 34.dp),
                text = stringResource(id = R.string.start_exam_challenge_condition),
            )
            Spacer(space = 4.dp)
            QuackHeadLine1(
                modifier = Modifier.padding(top = 4.dp),
                text = state.requirementQuestion, // TODO(EvergreenTree97) 추후 도전 조건 response 생기면 변경
            )
            StartExamTextField(
                modifier = Modifier.padding(top = 14.dp).clip(RoundedCornerShape(8.dp)),
                text = certifyingStatementText,
                alwaysPlaceholderVisible = false,
                placeholderText = "ex) ${state.requirementPlaceholder}",
                onTextChanged = viewModel::inputCertifyingStatement,
                keyboardActions = KeyboardActions {
                    keyboard?.hide()
                    viewModel.startSolveProblem()
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        TempFlexiblePrimaryLargeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            text = stringResource(id = R.string.start_exam_quiz_start_button),
            onClick = viewModel::startSolveProblem,
            enabled = certifyingStatementText.isNotEmpty(),
        )
        ImeSpacer()
    }
}

@Composable
private fun InfoBox(
    modifier: Modifier = Modifier,
    limitTime: Int,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        QuackTitle2(text = stringResource(id = R.string.start_exam_information_before_quiz_title))
        Spacer(space = 4.dp)
        QuackBody2(text = stringResource(id = R.string.start_exam_information_before_quiz_line1))
        QuackText(
            modifier = Modifier.span(
                texts = listOf(
                    stringResource(
                        id = R.string.start_exam_information_before_quiz_line2_highlight,
                        limitTime.toString(),
                    ),
                ),
                style = SpanStyle(
                    color = QuackColor.Black.value,
                    fontWeight = FontWeight.Bold,
                ),
            ),
            typography = QuackTypography.Body2,
            text = stringResource(id = R.string.start_exam_information_before_quiz_line2_prefix) + stringResource(
                id = R.string.start_exam_information_before_quiz_line2_infix,
                limitTime.toString(),
            ) + stringResource(id = R.string.start_exam_information_before_quiz_line2_postfix),
        )
    }
}
