/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.solve.problem.answer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.DuckieGridLayout
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.feature.solve.problem.answer.choice.ImageAnswerBox
import team.duckie.app.android.feature.solve.problem.answer.choice.TextAnswerBox
import team.duckie.app.android.feature.solve.problem.answer.shortanswer.ShortAnswerForm
import team.duckie.app.android.feature.solve.problem.viewmodel.state.InputAnswer

private val HorizontalPadding = PaddingValues(horizontal = 16.dp)

internal object TextFieldMargin {
    val Top = 16.dp
}

@Composable
internal fun ColumnScope.AnswerSection(
    pageIndex: Int,
    answer: Answer,
    inputAnswers: ImmutableList<InputAnswer>,
    updateInputAnswers: (page: Int, inputAnswer: InputAnswer) -> Unit,
    requestFocus: Boolean,
    keyboardController: SoftwareKeyboardController?,
    onShortAnswerSizeChanged: (IntSize) -> Unit,
) {
    when (answer) {
        is Answer.Choice -> {
            Column(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .padding(paddingValues = HorizontalPadding),
                verticalArrangement = Arrangement.spacedBy(space = 12.dp),
            ) {
                answer.choices.forEachIndexed { index, choice ->
                    key(choice.text) {
                        TextAnswerBox(
                            text = choice.text,
                            selected = index != -1 && index == inputAnswers[pageIndex].number,
                            onClick = {
                                updateInputAnswers(
                                    pageIndex,
                                    InputAnswer(
                                        number = index,
                                        answer = choice.text,
                                    ),
                                )
                            },
                        )
                    }
                }
            }
        }

        is Answer.ImageChoice -> {
            DuckieGridLayout(
                modifier = Modifier.padding(paddingValues = HorizontalPadding),
                items = answer.imageChoice,
                columns = 2,
                verticalPadding = 24.dp,
                horizontalPadding = 8.dp,
            ) { index, imageChoice ->
                ImageAnswerBox(
                    imageSrc = imageChoice.imageUrl,
                    text = imageChoice.text,
                    selected = index != -1 && index == inputAnswers[pageIndex].number,
                    onClick = {
                        updateInputAnswers(
                            pageIndex,
                            InputAnswer(
                                number = index,
                                answer = imageChoice.text,
                            ),
                        )
                    },
                )
            }
        }

        is Answer.Short -> {
            Spacer(space = TextFieldMargin.Top)
            ShortAnswerForm(
                modifier = Modifier.onSizeChanged(onShortAnswerSizeChanged),
                answer = answer.correctAnswer,
                onTextChanged = { inputText ->
                    updateInputAnswers(pageIndex, InputAnswer(0, inputText))
                },
                requestFocus = requestFocus,
                keyboardController = keyboardController,
            )
        }
    }
}
