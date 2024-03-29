/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.centerVertical
import team.duckie.app.android.common.compose.heightOrZero
import team.duckie.app.android.common.compose.ui.icon.v1.ArrowSendId
import team.duckie.app.android.common.compose.ui.quack.TextFieldDecorationLayoutId.LeadingId
import team.duckie.app.android.common.compose.ui.quack.TextFieldDecorationLayoutId.PlaceholderId
import team.duckie.app.android.common.compose.ui.quack.TextFieldDecorationLayoutId.TextFieldId
import team.duckie.app.android.common.compose.ui.quack.TextFieldDecorationLayoutId.TrailingId
import team.duckie.app.android.common.compose.util.rememberUserInputState
import team.duckie.app.android.common.compose.widthOrZero
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText

@NonRestartableComposable
@Composable
fun blackAndPrimaryColor(text: String) = animateColorAsState(
    targetValue = if (text.isEmpty()) QuackColor.Unspecified.value else QuackColor.DuckieOrange.value,
    label = "writeCommentButtonColor",
)

@Composable
fun QuackNoUnderlineTextField(
    modifier: Modifier = Modifier,
    text: String,
    textTypography: QuackTypography? = null,
    onTextChanged: (text: String) -> Unit,
    placeholderText: String? = null,
    paddingValues: PaddingValues = PaddingValues(
        top = 8.dp,
        bottom = 8.dp,
        start = 12.dp,
        end = 12.dp,
    ),
    startPadding: Dp = 0.dp,
    leadingIconOnClick: (() -> Unit)? = null,
    @DrawableRes leadingIcon: Int? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    trailingEndPadding: Dp = 0.dp,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconSize: Dp = 24.dp,
    trailingIconOnClick: (() -> Unit)? = null,
    trailingIconTint: Color = Color.Unspecified,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val quackTextFieldColors = QuackColor.DuckieOrange.value
    val isPlaceholder = text.isEmpty()

    val inputTypography = remember(
        key1 = isPlaceholder,
    ) {
        (textTypography ?: QuackTypography.Subtitle).runIf(
            condition = isPlaceholder,
        ) {
            change(
                color = QuackColor.Gray2,
            )
        }.asComposeStyle()
    }

    var userInputText by rememberUserInputState(
        defaultValue = text,
        updateState = onTextChanged,
    )

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = QuackColor.White.value,
            )
            .padding(paddingValues),
        value = userInputText,
        onValueChange = { userInputText = it },
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
                textTypography = textTypography,
                isPlaceholder = isPlaceholder,
                placeholderText = placeholderText,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                startPadding = startPadding,
                trailingContent = trailingContent,
                trailingStartPadding = trailingEndPadding,
                trailingIconOnClick = trailingIconOnClick,
                trailingIconSize = trailingIconSize,
                trailingIconTint = trailingIconTint,
                leadingIconOnClick = leadingIconOnClick,
            )
        },
    )
}

private object TextFieldDecorationLayoutId {
    const val TextFieldId = "TextField"
    const val PlaceholderId = "Hint"
    const val LeadingId = "Leading"
    const val TrailingId = "Trailing"
}

@Composable
private fun TextFieldDecoration(
    textField: @Composable () -> Unit,
    textTypography: QuackTypography? = null,
    isPlaceholder: Boolean,
    placeholderText: String?,
    @DrawableRes leadingIcon: Int?,
    trailingIcon: Int?,
    startPadding: Dp = 0.dp,
    leadingEndPadding: Dp = 0.dp,
    trailingContent: @Composable (() -> Unit)? = null,
    trailingStartPadding: Dp = 16.dp,
    trailingIconSize: Dp = 24.dp,
    leadingIconOnClick: (() -> Unit)? = null,
    trailingIconOnClick: (() -> Unit)?,
    trailingIconTint: Color = Color.Unspecified,
) = with(TextFieldDecorationLayoutId) {
    Layout(
        modifier = Modifier.fillMaxWidth(),
        content = {
            if (leadingIcon != null) {
                QuackImage(
                    modifier = Modifier
                        .layoutId(LeadingId)
                        .size(DpSize(16.dp, 16.dp))
                        .padding(end = leadingEndPadding)
                        .quackClickable(
                            onClick = leadingIconOnClick,
                            rippleEnabled = false,
                        )
                        .padding(end = 16.dp),
                    src = leadingIcon,
                )
            }
            if (isPlaceholder && placeholderText != null) {
                QuackText(
                    modifier = Modifier
                        .layoutId(PlaceholderId)
                        .padding(start = startPadding),
                    text = placeholderText,
                    typography = (textTypography ?: QuackTypography.Body1).change(
                        color = QuackColor.Gray2,
                    ),
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(
                modifier = Modifier.layoutId(TextFieldId),
            ) {
                textField()
            }
            if (trailingContent != null) {
                Box(
                    modifier = Modifier
                        .layoutId(TrailingId)
                        .quackClickable(
                            onClick = trailingIconOnClick,
                            rippleEnabled = false,
                        )
                        .padding(start = trailingStartPadding),
                ) {
                    trailingContent()
                }
            } else if (trailingIcon != null) {
                QuackImage(
                    modifier = Modifier
                        .layoutId(TrailingId)
                        .quackClickable(
                            onClick = trailingIconOnClick,
                            rippleEnabled = false,
                        )
                        .size(trailingIconSize)
                        .padding(start = trailingStartPadding),
                    src = trailingIcon,
                    tint = QuackColor(trailingIconTint), // TODO(limsaehuyn): conflict 방지용, 타입 자체를 QuackColor로 변경 요망
                )
            }
        },
        measurePolicy = remember { quackNoUnderlineTextFieldMeasurePolicy() },
    )
}

private fun quackNoUnderlineTextFieldMeasurePolicy() = MeasurePolicy { measurables, constraints ->
    val textConstraints = constraints.copy(minHeight = 0)
    val relaxedConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    val layoutWidth = constraints.maxWidth

    val trailingPlaceable = measurables.fastFirstOrNull { it.layoutId == TrailingId }
        ?.measure(relaxedConstraints)
    val leadingPlaceable =
        measurables.fastFirstOrNull { it.layoutId == LeadingId }?.measure(constraints)

    val placeHolderPlaceable = measurables.fastFirstOrNull { it.layoutId == PlaceholderId }
        ?.measure(textConstraints)

    val leadingWidth = leadingPlaceable.widthOrZero()
    val trailingWidth = trailingPlaceable.widthOrZero()
    val occupiedSpaceHorizontally = leadingWidth + trailingWidth

    val textFieldPlaceable = measurables.fastFirstOrNull { it.layoutId == TextFieldId }
        ?.measure(
            textConstraints.copy(minHeight = 0).offset(horizontal = -occupiedSpaceHorizontally),
        )

    val maxOfHeight = maxOf(
        trailingPlaceable.heightOrZero(),
        leadingPlaceable.heightOrZero(),
        placeHolderPlaceable.heightOrZero(),
        textFieldPlaceable.heightOrZero(),
    )

    layout(layoutWidth, maxOfHeight) {
        leadingPlaceable?.placeRelative(
            0,
            maxOfHeight.centerVertical(leadingPlaceable.heightOrZero()),
        )
        trailingPlaceable?.placeRelative(
            layoutWidth - trailingWidth,
            maxOfHeight.centerVertical(trailingPlaceable.heightOrZero()),
        )
        textFieldPlaceable?.placeRelative(
            leadingWidth,
            maxOfHeight.centerVertical(textFieldPlaceable.heightOrZero()),
        )
        placeHolderPlaceable?.placeRelative(
            leadingWidth,
            maxOfHeight.centerVertical(placeHolderPlaceable.heightOrZero()),
        )
    }
}

@Preview
@Composable
fun PreviewQuackNoUnderlineTextField() {
    val (value, onValueChanged) = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        QuackNoUnderlineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            text = value,
            onTextChanged = onValueChanged,
            placeholderText = "플레이스홀더",
//        leadingIcon = QuackIcon.Search,
            trailingIcon = QuackIcon.ArrowSendId,
            trailingIconOnClick = {},
            paddingValues = PaddingValues(
                vertical = 16.dp,
            ),
        )
        QuackNoUnderlineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(QuackColor.Gray4.value),
            text = value,
            onTextChanged = onValueChanged,
            placeholderText = stringResource(id = R.string.exam_result_input_comment_hint),
            trailingIcon = QuackIcon.ArrowSendId,
            trailingIconOnClick = { },
            paddingValues = PaddingValues(
                vertical = 14.dp,
                horizontal = 16.dp,
            ),
        )
    }
}
