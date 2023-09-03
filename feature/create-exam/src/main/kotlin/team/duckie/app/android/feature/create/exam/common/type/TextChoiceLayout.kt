/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalDesignToken::class,
    ExperimentalQuackQuackApi::class
)

package team.duckie.app.android.feature.create.exam.common.type

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.create.exam.R
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackBorder
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.sugar.QuackSubtitle
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/** 객관식/글 Layout */
@Composable
@Suppress("unused")
internal fun TextChoiceLayout(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    imageClick: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answers: Answer.Choice,
    answerTextChanged: (String, Int) -> Unit,
    addAnswerClick: () -> Unit,
    correctAnswers: String?,
    setCorrectAnswerClick: (String) -> Unit,
    deleteLongClick: (Int?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .quackClickable(
                onLongClick = { deleteLongClick(null) },
            ) {},
    ) {
        TitleView(
            questionIndex,
            question,
            titleChanged,
            imageClick,
            answers.type.title,
            onDropdownItemClick,
        )

        answers.choices.fastForEachIndexed { answerIndex, choiceModel ->
            val answerNo = answerIndex + 1
            val isChecked = correctAnswers == "$answerIndex"
            QuackDefaultTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .quackBorder(
                        border = QuackBorder(
                            color = if (isChecked) QuackColor.DuckieOrange else QuackColor.Gray4,
                        ),
                    )
                    .quackClickable(
                        onLongClick = { deleteLongClick(answerIndex) },
                    ) {},
                value = choiceModel.text,
                onValueChange = { newAnswer -> answerTextChanged(newAnswer, answerIndex) },
                placeholderText = stringResource(
                    id = R.string.create_problem_answer_placeholder,
                    "$answerNo",
                ),
                style = QuackTextFieldStyle.Default,
                // TODO(riflockle7): 꽥꽥 기능 제공 안함
                // trailingContent = {
                //     Column(
                //         modifier = Modifier.quackClickable(
                //             onClick = {
                //                 setCorrectAnswerClick(if (isChecked) "" else "$answerIndex")
                //             },
                //         ),
                //         horizontalAlignment = Alignment.CenterHorizontally,
                //     ) {
                //         QuackRoundCheckBox(checked = isChecked)
                //
                //         if (isChecked) {
                //             QuackText(
                //                 modifier = Modifier.padding(top = 2.dp),
                //                 typography = QuackTypography.Body3.change(QuackColor.DuckieOrange),
                //                 text = stringResource(id = R.string.answer),
                //             )
                //         }
                //     }
                // },
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (answers.choices.size < MaximumChoice) {
            QuackSubtitle(
                modifier = Modifier
                    .quackClickable(onClick = addAnswerClick)
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                text = stringResource(id = R.string.create_problem_add_button),
            )
        }
    }
}

