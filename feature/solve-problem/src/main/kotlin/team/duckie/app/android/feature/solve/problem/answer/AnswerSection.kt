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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.feature.solve.problem.answer.choice.ImageAnswerBox
import team.duckie.app.android.feature.solve.problem.answer.choice.TextAnswerBox
import team.duckie.app.android.feature.solve.problem.answer.shortanswer.ShortAnswerForm
import team.duckie.app.android.feature.solve.problem.viewmodel.state.InputAnswer
import team.duckie.app.android.shared.ui.compose.DuckieGridLayout

private val HorizontalPadding = PaddingValues(horizontal = 16.dp)

internal fun LazyListScope.answerSection(
    pageIndex: Int,
    answer: Answer,
    inputAnswers: ImmutableList<InputAnswer>,
    updateInputAnswers: (page: Int, inputAnswer: InputAnswer) -> Unit,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
) {
    when (answer) {
        is Answer.Choice -> {
            item {
                Column(
                    modifier = Modifier.padding(paddingValues = HorizontalPadding),
                    verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                ) {
                    answer.choices.forEachIndexed { index, choice ->
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
            item {
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
        }

        is Answer.Short -> {
            item {
                ShortAnswerForm(
                    modifier = Modifier.padding(paddingValues = HorizontalPadding),
                    answer = answer.correctAnswer,
                    text = inputAnswers[pageIndex].answer,
                    onTextChanged = { inputText ->
                        updateInputAnswers(pageIndex, InputAnswer(0, inputText))
                    },
                    onDone = { inputText ->
                        updateInputAnswers(pageIndex, InputAnswer(0, inputText))
                    },
                    focusRequester = focusRequester,
                    keyboardController = keyboardController,
                )
            }
        }
    }
}
