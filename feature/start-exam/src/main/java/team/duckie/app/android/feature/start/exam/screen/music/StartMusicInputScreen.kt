/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.start.exam.screen.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.common.compose.ui.AlertYellow
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
import team.duckie.quackquack.ui.sugar.QuackHeadLine1
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun StartMusicInputScreen(modifier: Modifier, viewModel: StartExamViewModel) {
    val state =
        viewModel.container.stateFlow.collectAsStateWithLifecycle().value as StartExamState.Input

    val certifyingStatementText: String = remember(state.certifyingStatementInputText) {
        state.certifyingStatementInputText
    }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(modifier = modifier.fillMaxWidth()) {
        BackPressedTopAppBar(onBackPressed = viewModel::finishStartExam)
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            InfoBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = QuackColor.AlertYellow.value)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                limitTime = state.timer,
            )

            QuackHeadLine1(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
                text = state.requirementQuestion,
            )
            StartExamTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(8.dp)),
                text = certifyingStatementText,
                alwaysPlaceholderVisible = false,
                placeholderText = "ex) ${state.requirementPlaceholder}",
                onTextChanged = viewModel::inputCertifyingStatement,
                keyboardActions = KeyboardActions {
                    keyboard?.hide()
                    viewModel.startSolveProblem()
                },
            )
        }
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
        TempFlexiblePrimaryLargeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            text = stringResource(id = R.string.start_exam_music_start_button),
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
        QuackTitle2(text = stringResource(id = R.string.start_exam_music_before_title))
        Spacer(space = 8.dp)
        Text(
            style = QuackTypography.Body2.asComposeStyle().copy(
                lineBreak = LineBreak.Simple,
            ),
            text = buildAnnotatedString {
                withBlackBoldStyle {
                    append(
                        stringResource(
                            id = R.string.start_exam_music_before_line1_prefix,
                            limitTime.toString(),
                        ),
                    )
                }
                append(stringResource(id = R.string.start_exam_music_before_line1_postfix))
            },
        )
        Text(
            style = QuackTypography.Body2.asComposeStyle().copy(
                lineBreak = LineBreak.Simple,
            ),
            text = buildAnnotatedString {
                append(stringResource(id = R.string.start_exam_music_before_line2_prefix))
                withBlackBoldStyle {
                    append(
                        stringResource(
                            id = R.string.start_exam_music_before_line2_infix,
                            limitTime.toString(),
                        ),
                    )
                }
                append(stringResource(id = R.string.start_exam_music_before_line2_postfix))
            },
        )
        Text(
            style = QuackTypography.Body2.asComposeStyle().copy(
                lineBreak = LineBreak.Simple,
            ),
            text = buildAnnotatedString {
                append(stringResource(id = R.string.start_exam_music_before_line3_prefix))
                withBlackBoldStyle {
                    append(stringResource(id = R.string.start_exam_music_before_line3_postfix))
                }
            },
        )
        Text(
            style = QuackTypography.Body2.asComposeStyle().copy(
                lineBreak = LineBreak.Simple,
            ),
            text = buildAnnotatedString {
                withBlackBoldStyle {
                    append(stringResource(id = R.string.start_exam_music_before_line4_prefix))
                }
                append(stringResource(id = R.string.start_exam_music_before_line4_postfix))
            },
        )
    }
}

private inline fun <R : Any> AnnotatedString.Builder.withBlackBoldStyle(
    block: AnnotatedString.Builder.() -> R,
): R {
    return withStyle(
        style = SpanStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        ),
    ) {
        block()
    }
}
