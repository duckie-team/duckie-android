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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.solve.problem.answer.shortanswer.ShortAnswerForm
import team.duckie.app.android.feature.solve.problem.viewmodel.state.InputAnswer
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

@AllowMagicNumber("to get flexible image height")
@NonRestartableComposable
@Composable
private fun getFlexibleImageHeight(): Dp {
    val configuration = LocalConfiguration.current
    return ((configuration.screenWidthDp / 4) * 3).dp
}

private object TextFieldMargin {
    val Top = 24.dp
    val Bottom = 16.dp
    val Vertical get() = Top + Bottom
}


/**
 * 키보드 상태에 따라서 이미지의 높이가 유동적으로 변하는 주관식 문제를 위한 섹션
 */
@Composable
fun FlexibleSubjectiveQuestionSection(
    problem: Problem,
    pageIndex: Int,
    updateInputAnswers: (page: Int, inputAnswer: InputAnswer) -> Unit,
    requestFocus: Boolean,
    keyboardController: SoftwareKeyboardController?,
) {
    val density = LocalDensity.current
    val question = problem.question as Question.Image
    val flexibleImageHeight = getFlexibleImageHeight()
    var textFieldHeight by remember { mutableStateOf(Dp.Unspecified) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .quackClickable(
                rippleEnabled = false,
            ) {
                keyboardController?.hide()
            },
    ) {
        Spacer(space = 16.dp)
        QuackHeadLine2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "${pageIndex + 1}. ${question.text}",
        )
        Spacer(space = 12.dp)
        BoxWithConstraints {
            val actualHeight =
                if (maxHeight - textFieldHeight - TextFieldMargin.Vertical >= flexibleImageHeight) {
                    flexibleImageHeight
                } else {
                    maxHeight - textFieldHeight - TextFieldMargin.Vertical
                }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = actualHeight)
                    .padding(horizontal = 16.dp)
                    .background(
                        color = QuackColor.Black.value,
                        shape = RoundedCornerShape(16.dp),
                    ),
            ) {
                QuackImage(
                    modifier = Modifier.fillMaxSize(),
                    src = question.imageUrl,
                    contentScale = ContentScale.Fit,
                )
            }
        }
        Spacer(space = TextFieldMargin.Top)
        ShortAnswerForm(
            modifier = Modifier.onSizeChanged {
                with(density) {
                    textFieldHeight = it.height.toDp()
                }
            },
            answer = problem.correctAnswer ?: "",
            onTextChanged = { inputText ->
                updateInputAnswers(pageIndex, InputAnswer(0, inputText))
            },
            requestFocus = requestFocus,
            keyboardController = keyboardController,
        )
        Spacer(space = TextFieldMargin.Bottom)
    }
}
