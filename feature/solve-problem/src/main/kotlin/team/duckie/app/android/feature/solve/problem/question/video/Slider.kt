/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.question.video

import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import team.duckie.app.android.common.compose.rememberNoRippleInteractionSource
import team.duckie.quackquack.material.QuackColor

@Composable
internal fun primarySliderColors() = SliderDefaults.colors(
    thumbColor = QuackColor.DuckieOrange.value,
    activeTrackColor = QuackColor.DuckieOrange.value,
    inactiveTrackColor = Color.Transparent,
)

@Composable
internal fun bufferSliderColors() = SliderDefaults.colors(
    disabledThumbColor = Color.Transparent,
    disabledActiveTrackColor = QuackColor.Gray2.value.copy(alpha = 0.5f),
    disabledInactiveTrackColor = QuackColor.Gray3.value,
)

@Composable
internal fun BottomSlider(
    modifier: Modifier,
    enabled: Boolean = true,
    value: Float,
    onValueChanged: (Float) -> Unit,
    colors: SliderColors = primarySliderColors(),
    range: ClosedFloatingPointRange<Float> = PercentRange,
) {
    val interactionSource = rememberNoRippleInteractionSource()
    Slider(
        modifier = modifier,
        value = value,
        enabled = enabled,
        onValueChange = onValueChanged,
        colors = colors,
        valueRange = range,
        interactionSource = interactionSource,
    )
}

private val PercentRange = 0f..100f
