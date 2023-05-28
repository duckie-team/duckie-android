/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.solve.problem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.rememberKeyboardVisible
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.solve.problem.answer.shortanswer.ShortAnswerForm
import team.duckie.app.android.feature.solve.problem.viewmodel.state.InputAnswer
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

/**
 * 키보드 상태에 따라서 이미지의 높이가 유동적으로 변하는 주관식 문제를 위한 섹션
 */
@Composable
fun FlexibleSubjectiveQuestionSection(
    problem: Problem,
    pageIndex: Int,
    inputAnswers: ImmutableList<InputAnswer>,
    updateInputAnswers: (page: Int, inputAnswer: InputAnswer) -> Unit,
    requestFocus: Boolean,
    keyboardController: SoftwareKeyboardController?,
) {
    val question = problem.question as Question.Image

    val keyboardVisible = rememberKeyboardVisible(initialKeyboardState = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(space = 16.dp)
        QuackHeadLine2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "${pageIndex + 1}. ${question.text}",
        )
        Spacer(space = 12.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = QuackColor.Black.value,
                    shape = RoundedCornerShape(8.dp),
                )
                .composed {
                    runIf(keyboardVisible.value) {
                        weight(1f)
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            QuackImage(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .composed {
                        runIf(keyboardVisible.value) {
                            fillMaxSize()
                        }
                    }
                    .align(Alignment.Center),
                src = question.imageUrl,
                contentScale = ContentScale.Fit,
            )
        }
        Spacer(space = 24.dp)
        ShortAnswerForm(
            modifier = Modifier.padding(horizontal = 16.dp),
            answer = problem.correctAnswer ?: "",
            text = inputAnswers[pageIndex].answer,
            onTextChanged = { inputText ->
                updateInputAnswers(pageIndex, InputAnswer(0, inputText))
            },
            onDone = { inputText ->
                updateInputAnswers(pageIndex, InputAnswer(0, inputText))
            },
            requestFocus = requestFocus,
            keyboardController = keyboardController,
        )
        Spacer(space = 16.dp)
    }
}
