/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.solveproblem.answer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.answer.choice.ImageAnswerBox
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.answer.choice.TextAnswerBox
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.answer.shortanswer.ShortAnswerForm
import team.duckie.app.android.feature.ui.solve.problem.solveproblem.viewmodel.state.InputAnswer
import team.duckie.app.android.shared.ui.compose.DuckieGridLayout

private val HorizontalPadding = PaddingValues(horizontal = 16.dp)

internal fun LazyListScope.answerSection(
    page: Int,
    answer: Answer,
    inputAnswers: ImmutableList<InputAnswer>,
    onSolveProblem: () -> Unit,
    onClickAnswer: (page: Int, inputAnswer: InputAnswer) -> Unit,
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
                            selected = index != -1 && index == inputAnswers[page].number,
                            onClick = {
                                onClickAnswer(
                                    page,
                                    InputAnswer(
                                        number = index,
                                        answer = choice.text,
                                    ),
                                )
                                onSolveProblem()
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
                        selected = index != -1 && index == inputAnswers[page].number,
                        onClick = {
                            onClickAnswer(
                                page,
                                InputAnswer(
                                    number = index,
                                    answer = imageChoice.text,
                                ),
                            )
                            onSolveProblem()
                        },
                    )
                }
            }
        }

        is Answer.Short -> {
            item {
                ShortAnswerForm(
                    modifier = Modifier.padding(paddingValues = HorizontalPadding),
                    answer = answer.answer.text,
                    onDone = { inputText -> // TODO(EvergreenTree97): 서버에 보낼 정답 text
                        onClickAnswer(
                            page,
                            InputAnswer(
                                number = 0,
                                answer = inputText,
                            ),
                        )
                        onSolveProblem()
                    },
                )
            }
        }
    }
}
