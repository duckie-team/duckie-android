/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.create.exam.common.type

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.todo.QuackDropDownCard
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.create.exam.R
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.Image
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.trailingIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/** 문제 항목 Layout 내 공통 제목 Layout */
@Composable
internal fun TitleView(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    imageClick: () -> Unit,
    dropDownTitle: String,
    onDropdownItemClick: (Int) -> Unit,
) {
    // TODO(riflockle7): 동작 확인 필요
    // TODO(riflockle7): 최상단 Line 없는 TextField 필요
    QuackDefaultTextField(
        modifier = Modifier.trailingIcon(
            icon = OutlinedGroup.Image,
            onClick = imageClick,
        ),
        value = question?.text ?: "",
        onValueChange = titleChanged,
        style = QuackTextFieldStyle.Default,
        placeholderText = stringResource(
            id = R.string.create_problem_question_placeholder,
            "${questionIndex + 1}",
        ),
    )

    (question as? Question.Image)?.imageUrl?.let {
        QuackImage(
            modifier = Modifier
                .padding(top = 24.dp)
                .size(DpSize(200.dp, 200.dp)),
            src = it,
        )
    }

    // TODO(riflockle7): border 없는 DropDownCard 필요
    QuackDropDownCard(
        modifier = Modifier.padding(top = 24.dp),
        text = dropDownTitle,
        onClick = {
            onDropdownItemClick(questionIndex)
        },
    )
}
