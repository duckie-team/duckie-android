/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackImage

private object DuckieFitImageStyle {
    val HorizontalPadding = PaddingValues(horizontal = 16.dp)
    val BackgroundColor = QuackColor.Gray4.value
    val BackgroundShape = RoundedCornerShape(8.dp)
    val ContentScaleType = ContentScale.Fit
}

/**
 * 덕키에서 이미지를 보여줄 때 주로 사용되는 컴포넌트입니다.
 *
 * 다음과 같은 특징을 같습니다.
 * 1. [imageUrl]에 해당하는 이미지를 보여줍니다.
 * 2. [CoverImageRatio] 비율로 보여줍니다.
 * 3. 이미지를 [DuckieFitImageStyle.ContentScaleType] 스타일로 보여줍니다.
 * 4. [DuckieFitImageStyle.BackgroundColor] 색상으로 배경을 채웁니다.
 */
@Composable
fun DuckieFitImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    horizontalPadding: PaddingValues = DuckieFitImageStyle.HorizontalPadding,
) = with(DuckieFitImageStyle) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontalPadding)
            .background(
                color = BackgroundColor,
                shape = BackgroundShape,
            )
            .aspectRatio(CoverImageRatio),
        contentAlignment = Alignment.Center,
    ) {
        QuackImage(
            modifier = Modifier.fillMaxSize(),
            src = imageUrl,
            contentScale = ContentScaleType,
        )
    }
}
