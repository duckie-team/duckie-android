/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.ui.solve.problem.answer.shortanswer

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import team.duckie.app.android.feature.ui.solve.problem.R
import team.duckie.quackquack.ui.component.QuackGrayscaleTextField

@Composable
internal fun ShortAnswerForm(
    modifier: Modifier = Modifier,
    answer: String,
    onDone: (String) -> Unit,
) {
    var value by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    QuackGrayscaleTextField(
        modifier = modifier,
        text = value,
        onTextChanged = {
            if (it.length <= answer.length) {
                value = it
            }
        },
        placeholderText = stringResource(id = R.string.length_contains_space, answer.length),
        imeAction = ImeAction.Done,
        maxLength = answer.length,
        showCounter = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onDone(value)
            },
        ),
    )
}
