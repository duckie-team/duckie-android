/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose.quack

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.component.QuackBasic2TextField
import team.duckie.quackquack.ui.icon.QuackIcon

/**
 * [QuackBasic2TextField] 를 변형해서 구현한 underline이 없는 TextField
 */
@Composable
fun QuackNoUnderlineTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String? = null,
    leadingStartPadding: Dp = 0.dp,
    leadingIcon: QuackIcon? = null,
    leadingIconOnClick: (() -> Unit)? = null,
    trailingEndPadding: Dp = 0.dp,
    trailingIcon: QuackIcon? = null,
    trailingIconOnClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    QuackBasic2TextField(
        modifier = modifier,
        text = text,
        onTextChanged = onTextChanged,
        placeholderText = placeholderText,
        leadingStartPadding = leadingStartPadding,
        leadingIcon = leadingIcon,
        leadingIconOnClick = leadingIconOnClick,
        trailingEndPadding = trailingEndPadding,
        trailingIcon = trailingIcon,
        trailingIconOnClick = trailingIconOnClick,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        showIndicatorLine = false,
    )
}
