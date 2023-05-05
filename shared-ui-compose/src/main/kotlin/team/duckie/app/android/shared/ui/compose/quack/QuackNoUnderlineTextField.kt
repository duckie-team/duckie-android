/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose.quack

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.util.kotlin.runIf
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

@Composable
fun QuackNoUnderlineTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String? = null,
    trailingEndPadding: Dp = 0.dp,
    @DrawableRes
    trailingIcon: Int? = null,
    trailingIconOnClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val quackTextFieldColors = QuackColor.DuckieOrange.composeColor
    val isPlaceholder = text.isEmpty()

    val inputTypography = remember(
        key1 = isPlaceholder,
    ) {
        QuackTextStyle.Subtitle.runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }.asComposeStyle()
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = QuackColor.White.composeColor,
            )
            .padding(
                paddingValues = PaddingValues(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 12.dp,
                ),
            ),
        value = text,
        onValueChange = onTextChanged,
        textStyle = inputTypography,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        cursorBrush = SolidColor(
            value = quackTextFieldColors,
        ),
        decorationBox = { textField ->
            TextFieldDecoration(
                textField = textField,
                isPlaceholder = isPlaceholder,
                placeholderText = placeholderText,
                trailingIcon = trailingIcon,
                trailingEndPadding = trailingEndPadding,
                trailingIconOnClick = trailingIconOnClick,
            )
        },
    )
}

@Composable
private fun TextFieldDecoration(
    textField: @Composable () -> Unit,
    isPlaceholder: Boolean,
    placeholderText: String?,
    @DrawableRes
    trailingIcon: Int?,
    trailingEndPadding: Dp = 0.dp,
    trailingIconOnClick: (() -> Unit)?,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart,
    ) {
        textField()
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isPlaceholder && placeholderText != null) {
                Box(
                    propagateMinConstraints = true,
                ) {
                    Text(
                        text = placeholderText,
                        style = QuackTextStyle.Body1.asComposeStyle().copy(
                            color = QuackColor.Gray2.composeColor,
                        ),
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            } else {
                null
            }
            Spacer(modifier = Modifier.weight(1f))
            if (trailingIcon != null) {
                QuackImage(
                    src = trailingIcon,
                    modifier = Modifier.padding(end = trailingEndPadding),
                    onClick = trailingIconOnClick,
                    size = DpSize(16.dp, 16.dp),
                    rippleEnabled = false,
                )
            }
        }
    }
}
