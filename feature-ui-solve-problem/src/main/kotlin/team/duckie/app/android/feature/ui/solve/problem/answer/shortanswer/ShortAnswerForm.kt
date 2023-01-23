/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.ui.solve.problem.answer.shortanswer

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue

internal const val WhiteSpace = '*'

@Composable
internal fun ShortAnswerForm(
    modifier: Modifier = Modifier,
    answer: String,
    onDone: () -> Unit,
) {
    var value by remember { mutableStateOf(TextFieldValue()) } // replace '*' to " " 필요
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    EachCharTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value,
        onValueChanged = { newValue, prevLength ->
            value = newValue
            if (
                newValue.text.isNotEmpty() &&
                newValue.text.lastIndex < answer.length &&
                answer[newValue.text.lastIndex].isWhitespace() &&
                newValue.text.length > prevLength
            ) {
                val lastChar = newValue.text.last()
                value = value.copy(
                    text = newValue.text.dropLast(1).plus(WhiteSpace) + lastChar,
                    selection = TextRange(value.selection.end + 1),
                )
            } else if (
                newValue.text.lastIndex > 0 &&
                answer[newValue.text.lastIndex].isWhitespace() &&
                newValue.text.length < prevLength
            ) {
                value = value.copy(
                    text = newValue.text.dropLast(1),
                    selection = TextRange(value.selection.end - 1),
                )
            }
        },
        answer = answer,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onDone()
            },
        ),
    )
}
