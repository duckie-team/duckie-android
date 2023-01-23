/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.answer.shortanswer

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import team.duckie.app.android.domain.exam.model.Answer

internal const val WhiteSpace = '*'

@Composable
internal fun ShortAnswerForm(
    answer: Answer,
) {
    val shortAnswer = (answer as Answer.Short).answer.text
    var value by remember { mutableStateOf(TextFieldValue()) } // replace '*' to " " 필요
    EachCharTextField(
        value = value,
        onValueChanged = { newValue, prevLength ->
            value = newValue
            if (
                newValue.text.isNotEmpty() &&
                newValue.text.lastIndex < shortAnswer.length
                && shortAnswer[newValue.text.lastIndex].isWhitespace()
                && newValue.text.length > prevLength
            ) {
                val lastChar = newValue.text.last()
                value = value.copy(
                    text = newValue.text.dropLast(1).plus(WhiteSpace) + lastChar,
                    selection = TextRange(value.selection.end + 1)
                )
            } else if (
                newValue.text.lastIndex > 0
                && shortAnswer[newValue.text.lastIndex].isWhitespace()
                && newValue.text.length < prevLength
            ) {
                value = value.copy(
                    text = newValue.text.dropLast(1),
                    selection = TextRange(value.selection.end - 1)
                )
            }
        },
        answer = shortAnswer,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {},
        )
    )
}
