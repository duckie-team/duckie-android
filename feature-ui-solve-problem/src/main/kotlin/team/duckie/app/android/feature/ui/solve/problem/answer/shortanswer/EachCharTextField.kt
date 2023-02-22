/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.answer.shortanswer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.shared.ui.compose.StaggeredLayout
import team.duckie.app.android.util.kotlin.fastForEachIndexed
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackSurface

@Composable
internal fun EachCharTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChanged: (TextFieldValue, Int) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    answer: String,
) {
    val answerChars = remember { answer.toImmutableList() }
    val remainCount = answerChars.size - value.text.length

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.text.length <= answer.length) {
                onValueChanged(it, value.text.length)
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = {
            StaggeredLayout {
                value.text.toImmutableList().fastForEachIndexed { index, char ->
                    if (char == WhiteSpace) {
                        Spacer(modifier = Modifier.padding(start = 12.dp))
                    } else {
                        CharBox(
                            char = char,
                            isFocused = index == value.text.lastIndex,
                        )
                    }
                }
                if (remainCount > 0) {
                    repeat(times = remainCount) { index ->
                        if (answerChars[index + value.text.length].isWhitespace()) {
                            Spacer(modifier = Modifier.padding(start = 12.dp))
                        } else {
                            CharBox(
                                char = ' ',
                                isFocused = false,
                            )
                        }
                    }
                }
            }
        },
    )
}

@Composable
private fun CharBox(
    char: Char,
    isFocused: Boolean,
) {
    QuackSurface(
        modifier = Modifier.sizeIn(
            minWidth = 29.dp,
            minHeight = 40.dp,
        ),
        backgroundColor = QuackColor.Gray4,
        shape = RoundedCornerShape(size = 4.dp),
        border = setCharBoxBorder(isFocused = isFocused),
    ) {
        QuackBody1(
            padding = PaddingValues(
                horizontal = 8.dp,
                vertical = 10.dp,
            ),
            text = char.toString(),
        )
    }
}

@Stable
private fun setCharBoxBorder(
    isFocused: Boolean,
) = when (isFocused) {
    true -> QuackBorder(
        width = 1.dp,
        color = QuackColor.DuckieOrange,
    )

    else -> null
}
