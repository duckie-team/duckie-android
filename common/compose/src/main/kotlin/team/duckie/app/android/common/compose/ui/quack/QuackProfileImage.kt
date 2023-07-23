/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import team.duckie.app.android.common.compose.R
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.material.shape.SquircleShape

@DrawableRes
private val DefaultProfileImage: Int = R.drawable.ic_default_profile

/**
 * [QuackProfileImage] 다음과 같은 특징을 같습니다.
 * 1. [profileUrl] 기 null 일 경우 [DefaultProfileImage] 를 보여줍니다.
 * 2. [profileUrl] 을 로드 중 에러가 날 경우 [DefaultProfileImage] 를 보여줍니다.
 * 3. [contentScale] 을 기본적으로 [ContentScale.Crop] 으로 설정합니다.
 * 4. [shape] 를 기본적으로 [SquircleShape] 로 설정합니다.
 */
@Composable
fun QuackProfileImage(
    modifier: Modifier = Modifier,
    profileUrl: String?,
    size: DpSize = DpSize(width = 44.dp, height = 44.dp),
    shape: Shape = SquircleShape,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context = context)
        .error(DefaultProfileImage)
        .allowHardware(false)
        .build()

    val asyncImagePainter = rememberAsyncImagePainter(
        model = profileUrl,
        imageLoader = imageLoader,
    )

    Image(
        modifier = modifier
            .size(size)
            .clip(shape)
            .quackClickable(
                rippleEnabled = false,
            ) {
                if (onClick != null) {
                    onClick()
                }
            },
        painter = asyncImagePainter,
        contentScale = contentScale,
        contentDescription = contentDescription,
    )
}
