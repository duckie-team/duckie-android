/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun QuackErrorableTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String,
    maxLength: Int,
    isError: Boolean,
    errorText: String,
    showClearButton: Boolean = false,
    onCleared: (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    // TODO(sungbin): https://github.com/duckie-team/quack-quack-android/issues/438
    team.duckie.quackquack.ui.component.QuackErrorableTextField(
        modifier = modifier,
        text = text,
        onTextChanged = onTextChanged,
        placeholderText = placeholderText,
        maxLength = maxLength,
        isError = isError,
        errorText = errorText,
        showClearButton = showClearButton,
        onCleared = onCleared,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
    )
}
