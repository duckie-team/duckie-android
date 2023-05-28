/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:OptIn(ExperimentalComposeUiApi::class)

package team.duckie.app.android.feature.solve.problem.answer.shortanswer

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.quackquack.ui.component.QuackGrayscaleTextField

@Composable
internal fun ShortAnswerForm(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    answer: String,
    onDone: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    requestFocus: Boolean,
) {

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    QuackGrayscaleTextField(
        modifier = modifier.focusRequester(focusRequester),
        text = text,
        onTextChanged = {
            if (it.length <= answer.length) {
                onTextChanged(it)
            }
        },
        placeholderText = stringResource(id = R.string.length_contains_space, answer.length),
        imeAction = ImeAction.Done,
        maxLength = answer.length,
        showCounter = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onDone(text)
            },
        ),
    )
}
