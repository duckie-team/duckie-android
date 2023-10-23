/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.create.exam.common.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.todo.QuackDropDownCard
import team.duckie.app.android.common.compose.ui.quack.todo.animation.QuackRoundCheckBox
import team.duckie.app.android.common.compose.util.rememberUserInputState
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.create.exam.R
import team.duckie.app.android.feature.create.exam.common.NoLazyGridItems
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.material.icon.quackicon.outlined.Image
import team.duckie.quackquack.material.quackBorder
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.sugar.QuackSubtitle
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/**
 * 객관식/사진 Layout
 * // TODO(riflockle7): 정답 체크 연동 필요
 */
@Composable
internal fun ImageChoiceLayout(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    onImageClick: () -> Unit,
    onImageClear: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answers: Answer.ImageChoice,
    answerTextChanged: (String, Int) -> Unit,
    answerImageClick: (Int) -> Unit,
    addAnswerClick: () -> Unit,
    correctAnswers: String?,
    setCorrectAnswerClick: (String) -> Unit,
    onProblemLongClick: ((Int?) -> Unit)?,
    onChoiceItemLongClick: (Int?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .runIf(onProblemLongClick != null) {
                quackClickable(
                    onLongClick = { onProblemLongClick?.invoke(null) },
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
            text = answers.type.title,
            showBorder = false,
            onClick = {
                onDropdownItemClick(questionIndex)
            },
        )

        // 문제 목록
        NoLazyGridItems(
            count = answers.imageChoice.size,
            nColumns = 2,
            paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            itemContent = { answerIndex ->
                val answerNo = answerIndex + 1
                val answerItem = answers.imageChoice[answerIndex]
                val isChecked = correctAnswers == "$answerIndex"
                var imageChoiceUserInput by rememberUserInputState(
                    defaultValue = answers.imageChoice[answerIndex].text,
                    updateState = { newAnswer ->
                        answerTextChanged(newAnswer, answerIndex)
                    },
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .quackBorder(border = QuackBorder(color = QuackColor.Gray4))
                        .quackBorder(
                            border = QuackBorder(
                                color = if (isChecked) {
                                    QuackColor.DuckieOrange
                                } else {
                                    QuackColor.Gray4
                                },
                            ),
                        )
                        .padding(12.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        QuackRoundCheckBox(
                            modifier = Modifier.quackClickable(
                                onClick = {
                                    setCorrectAnswerClick(if (isChecked) "" else "$answerIndex")
                                },
                            ),
                            checked = isChecked,
                        )

                        if (isChecked) {
                            QuackText(
                                modifier = Modifier.padding(start = 2.dp),
                                typography = QuackTypography.Body3.change(QuackColor.DuckieOrange),
                                text = stringResource(id = R.string.answer),
                            )
                        }

                        Spacer(Modifier.weight(1f))

                        QuackIcon(
                            icon = OutlinedGroup.Close,
                            modifier = Modifier
                                .quackClickable(
                                    onClick = { onChoiceItemLongClick(answerIndex) },
                                )
                                .size(DpSize(20.dp, 20.dp)),
                        )
                    }

                    if (answerItem.imageUrl.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .quackClickable { answerImageClick(answerIndex) }
                                .background(color = QuackColor.Gray4.value)
                                .padding(52.dp),
                        ) {
                            QuackIcon(
                                modifier = Modifier.size(DpSize(32.dp, 32.dp)),
                                icon = OutlinedGroup.Image,
                            )
                        }
                    } else {
                        QuackImage(
                            modifier = Modifier
                                .quackClickable(
                                    onClick = { answerImageClick(answerIndex) },
                                    onLongClick = { onChoiceItemLongClick(answerIndex) },
                                )
                                .size(DpSize(136.dp, 136.dp)),
                            src = answerItem.imageUrl,
                        )
                    }

                    // TODO(riflockle7): 동작 확인 필요
                    QuackDefaultTextField(
                        value = imageChoiceUserInput,
                        onValueChange = { imageChoiceUserInput = it },
                        placeholderText = stringResource(
                            id = R.string.create_problem_answer_placeholder,
                            "$answerNo",
                        ),
                        style = QuackTextFieldStyle.Default,
                    )
                }
            },
        )

        // 공백
        Spacer(modifier = Modifier.height(12.dp))

        // + 보기추가 버튼
        if (answers.imageChoice.size < MaximumChoice) {
            QuackSubtitle(
                modifier = Modifier
                    .quackClickable(onClick = addAnswerClick)
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                text = stringResource(id = R.string.create_problem_add_button),
            )
        }
    }
}
