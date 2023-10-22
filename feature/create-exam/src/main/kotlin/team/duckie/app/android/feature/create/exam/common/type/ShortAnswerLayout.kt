/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.create.exam.common.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.todo.QuackDropDownCard
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.create.exam.R
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/** 주관식 Layout */
@Composable
internal fun ShortAnswerLayout(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    onImageClick: () -> Unit,
    onImageClear: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answer: String,
    answerTextChanged: (String, Int) -> Unit,
    onProblemLongClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .runIf(onProblemLongClick != null) {
                quackClickable(
                    onLongClick = { onProblemLongClick?.invoke() },
                    onClick = null,
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
            text = Answer.Type.ShortAnswer.title,
            showBorder = false,
            onClick = {
                onDropdownItemClick(questionIndex)
            },
        )

        // 문제 항목
        // TODO(riflockle7): 동작 확인 필요
        QuackDefaultTextField(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            value = answer,
            onValueChange = { newAnswer -> answerTextChanged(newAnswer, 0) },
            placeholderText = stringResource(id = R.string.create_problem_short_answer_placeholder),
            style = QuackTextFieldStyle.Default,
        )
    }
}
