/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalDesignToken::class,
    ExperimentalDesignToken::class,
    ExperimentalQuackQuackApi::class,
)

package team.duckie.app.android.feature.create.exam.common.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.common.compose.ui.quack.todo.QuackDropDownCard
import team.duckie.app.android.common.compose.ui.quack.todo.animation.QuackRoundCheckBox
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.create.exam.R
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackBorder
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText
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
    onImageClick: () -> Unit,
    onImageClear: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answers: Answer.Choice,
    answerTextChanged: (String, Int) -> Unit,
    addAnswerClick: () -> Unit,
    correctAnswers: String?,
    setCorrectAnswerClick: (String) -> Unit,
    onProblemLongClick: ((Int?) -> Unit)? = null,
    onChoiceItemLongClick: (Int?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .runIf(onProblemLongClick != null) {
                quackClickable(
                    onClick = null,
                    onLongClick = { onProblemLongClick?.invoke(null) },
                )
            },
    ) {
        // 제목 뷰
        TitleView(
            questionIndex,
            question,
            titleChanged,
            onImageClick,
            onImageClear,
        )

        // 구분선
        Box(
            modifier = Modifier
                .background(QuackColor.Gray4.value)
                .fillMaxWidth()
                .height(8.dp),
        )

        // 문제 타입 선택 DropDown
        QuackDropDownCard(
            modifier = Modifier.padding(start = 4.dp, top = 28.dp),
            text = answers.type.title,
            showBorder = false,
            onClick = {
                onDropdownItemClick(questionIndex)
            },
        )

        // 문제 목록
        answers.choices.fastForEachIndexed { answerIndex, choiceModel ->
            val answerNo = answerIndex + 1
            val isChecked = correctAnswers == "$answerIndex"
            QuackNoUnderlineTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                    .quackBorder(
                        border = QuackBorder(
                            color = if (isChecked) QuackColor.DuckieOrange else QuackColor.Gray4,
                        ),
                    )
                    .quackClickable(
                        onLongClick = { onChoiceItemLongClick(answerIndex) },
                    ) {},
                text = choiceModel.text,
                onTextChanged = { newAnswer -> answerTextChanged(newAnswer, answerIndex) },
                paddingValues = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
                placeholderText = stringResource(
                    id = R.string.create_problem_answer_placeholder,
                    "$answerNo",
                ),
                trailingContent = {
                    Column(
                        modifier = Modifier.quackClickable(
                            onClick = {
                                setCorrectAnswerClick(if (isChecked) "" else "$answerIndex")
                            },
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        QuackRoundCheckBox(checked = isChecked)

                        if (isChecked) {
                            QuackText(
                                modifier = Modifier.padding(top = 2.dp),
                                typography = QuackTypography.Body3.change(QuackColor.DuckieOrange),
                                text = stringResource(id = R.string.answer),
                            )
                        }
                    }
                },
            )
        }

        // 공백
        Spacer(modifier = Modifier.height(12.dp))

        // + 보기추가 버튼
        if (answers.choices.size < MaximumChoice) {
            QuackSubtitle(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .quackClickable(onClick = addAnswerClick)
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                text = stringResource(id = R.string.create_problem_add_button),
            )
        }
    }
}
