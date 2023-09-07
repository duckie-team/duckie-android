/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.temp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.modifier.quackClickable

/**
 * QuackPrimaryLargeButton의 크키가 조정되지 않는 오류를 대처하기 위한 임시 버튼
 *
 * TODO(limsaehyun): 추후 QuackQuackV2 로 대체되어야 함
 */
@Composable
fun TempFlexiblePrimaryLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .heightIn(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundFor(enabled))
            .quackClickable(onClick = onClickFor(enabled, onClick)),
        contentAlignment = Alignment.Center,
    ) {
        QuackText(
            text = text,
            typography = QuackTypography.Subtitle.change(
                color = QuackColor.White,
            ),
        )
    }
}

private fun backgroundFor(enabled: Boolean) = if (enabled) {
    QuackColor.DuckieOrange.value
} else {
    QuackColor.Gray2.value
}

private fun onClickFor(
    enabled: Boolean,
    onClick: () -> Unit,
) = if (enabled) {
    onClick
} else {
    null
}
