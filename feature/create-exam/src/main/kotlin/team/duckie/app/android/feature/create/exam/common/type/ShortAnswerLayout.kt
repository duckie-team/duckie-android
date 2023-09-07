/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.create.exam.common.type

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.create.exam.R
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
    imageClick: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answer: String,
    answerTextChanged: (String, Int) -> Unit,
    deleteLongClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .quackClickable(
                onLongClick = { deleteLongClick() },
            ) {},
    ) {
        TitleView(
            questionIndex,
            question,
            titleChanged,
            imageClick,
            Answer.Type.ShortAnswer.title,
            onDropdownItemClick,
        )

        // TODO(riflockle7): 동작 확인 필요
        QuackDefaultTextField(
            value = answer,
            onValueChange = { newAnswer -> answerTextChanged(newAnswer, 0) },
            placeholderText = stringResource(id = R.string.create_problem_short_answer_placeholder),
            style = QuackTextFieldStyle.Default,
        )
    }
}
