/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.icon.v1.ArrowSendId
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackImage

@Composable
fun QuackNoUnderlineTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String? = null,
    paddingValues: PaddingValues = PaddingValues(
        vertical = 8.dp,
        horizontal = 12.dp
    ),
    startPadding: Dp = 0.dp,
    @DrawableRes leadingIcon: Int? = null,
    trailingEndPadding: Dp = 0.dp,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconSize: Dp = 24.dp,
    trailingIconOnClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val quackTextFieldColors = QuackColor.DuckieOrange.value
    val isPlaceholder = text.isEmpty()

    val inputTypography = remember(
        key1 = isPlaceholder,
    ) {
        QuackTypography.Subtitle.runIf(
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
                color = QuackColor.White.value,
            )
            .padding(paddingValues),
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
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                startPadding = startPadding,
                trailingEndPadding = trailingEndPadding,
                trailingIconOnClick = trailingIconOnClick,
                trailingIconSize = trailingIconSize,
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
    leadingIcon: Int?,
    trailingIcon: Int?,
    startPadding: Dp = 0.dp,
    trailingEndPadding: Dp = 0.dp,
    trailingIconSize: Dp = 24.dp,
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
            if (leadingIcon != null) {
                QuackImage(
                    modifier = Modifier
                        .size(DpSize(16.dp, 16.dp))
                        .padding(end = trailingEndPadding)
                        .quackClickable(
                            onClick = trailingIconOnClick,
                            rippleEnabled = false,
                        ),
                    src = leadingIcon,
                )
            }

            if (isPlaceholder && placeholderText != null) {
                Box(
                    propagateMinConstraints = true,
                ) {
                    Text(
                        modifier = Modifier.padding(start = startPadding),
                        text = placeholderText,
                        style = QuackTypography.Body1.asComposeStyle().copy(
                            color = QuackColor.Gray2.value,
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
                    modifier = Modifier
                        .quackClickable(
                            onClick = trailingIconOnClick,
                            rippleEnabled = false,
                        )
                        .size(trailingIconSize)
                        .padding(end = trailingEndPadding),
                    src = trailingIcon,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewQuackNoUnderlineTextField() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        QuackNoUnderlineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            text = "텍스트",
            onTextChanged = {},
            placeholderText = "플레이스홀더",
//        leadingIcon = QuackIcon.Search,
        trailingIcon = QuackIcon.ArrowSendId,
            trailingIconOnClick = {},
            paddingValues = PaddingValues(
                vertical = 16.dp
            )
        )
        QuackNoUnderlineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(QuackColor.Gray4.value),
            text = "댓글을 남겨보세요!",
            onTextChanged = { },
            placeholderText = "댓글을 남겨보세요!",
            trailingIcon = QuackIcon.ArrowSendId,
            trailingIconOnClick = { },
            paddingValues = PaddingValues(
                vertical = 14.dp,
                horizontal = 16.dp,
            )
        )
    }
}
