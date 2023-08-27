/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.solve.problem.answer.shortanswer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackBorder
import team.duckie.quackquack.ui.sugar.QuackBody1

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ShortAnswerForm(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    answer: String,
    keyboardController: SoftwareKeyboardController?,
    requestFocus: Boolean,
) {
    val focusRequester = remember { FocusRequester() }
    val hasFocus = remember { mutableStateOf(false) }
    val myAnswer = remember { mutableStateOf("") }

    LaunchedEffect(key1 = requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Box(modifier) {
        TextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    hasFocus.value = it.hasFocus
                }
                .background(Color.Transparent),
            value = myAnswer.value,
            onValueChange = {
                if (it.length <= answer.replace(" ", "").length) {
                    myAnswer.value = it
                    onTextChanged(it)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    },
                ),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ExamOneText.toData(answer = answer).forEachIndexed { index, data ->
                if (getBlankIndexes(answer).contains(data.id)) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
                ShortAnswerOneTextForm(
                    index = index,
                    myAnswer = myAnswer.value,
                    isFocused = hasFocus.value && (myAnswer.value.length - 1 == data.id || (myAnswer.value.isEmpty() && data.id == 0)),
                )
            }
        }
    }
}

@Composable
private fun ShortAnswerOneTextForm(
    index: Int,
    myAnswer: String,
    isFocused: Boolean,
) {
    Column {
        Box(
            modifier = Modifier
                .widthIn(30.dp)
                .heightIn(40.dp)
                .clip(RoundedCornerShape(4.dp))
                .quackBorder(
                    border = QuackBorder(color = if (isFocused) QuackColor.DuckieOrange else QuackColor.Gray3),
                    shape = RoundedCornerShape(4.dp),
                )
                .background(QuackColor.Gray4.value),
            contentAlignment = Alignment.Center,
        ) {
            QuackBody1(
                text = if (myAnswer.length > index) myAnswer[index].toString() else "",
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

private fun getBlankIndexes(answer: String): List<Int> {
    val blankIndex: ArrayList<Int> = ArrayList()
    answer.forEachIndexed { index, char ->
        if (char == ' ') {
            blankIndex.add(index - blankIndex.size)
        }
    }
    return blankIndex
}

private data class ExamOneText(
    val id: Int,
    val value: String,
) {
    companion object {
        fun toData(answer: String): List<ExamOneText> {
            return answer.replace(" ", "")
                .mapIndexed { index, c -> ExamOneText(index, c.toString()) }
        }
    }
}
