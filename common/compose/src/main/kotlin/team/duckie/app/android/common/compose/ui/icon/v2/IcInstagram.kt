/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.icon.v2

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.QuackIcon

public val QuackIcon.Instagram: ImageVector
    get() {
        if (_icInstagram != null) {
            return _icInstagram!!
        }
        _icInstagram = Builder(
            name = "IcInstagram",
            defaultWidth = 25.0.dp,
                defaultHeight =
            24.0.dp,
                viewportWidth = 25.0f,
            viewportHeight = 24.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF222222)),
                strokeLineWidth = 1.3f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(8.5f, 4.65f)
                lineTo(16.5f, 4.65f)
                arcTo(3.35f, 3.35f, 0.0f, false, true, 19.85f, 8.0f)
                lineTo(19.85f, 16.0f)
                arcTo(3.35f, 3.35f, 0.0f, false, true, 16.5f, 19.35f)
                lineTo(8.5f, 19.35f)
                arcTo(3.35f, 3.35f, 0.0f, false, true, 5.15f, 16.0f)
                lineTo(5.15f, 8.0f)
                arcTo(3.35f, 3.35f, 0.0f, false, true, 8.5f, 4.65f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF222222)),
                strokeLineWidth = 1.3f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(12.5f, 12.0f)
                moveToRelative(-3.35f, 0.0f)
                arcToRelative(3.35f, 3.35f, 0.0f, true, true, 6.7f, 0.0f)
                arcToRelative(3.35f, 3.35f, 0.0f, true, true, -6.7f, 0.0f)
            }
            path(
                fill = SolidColor(Color(0xFF222222)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(16.9444f, 7.5556f)
                moveToRelative(-0.8889f, 0.0f)
                arcToRelative(0.8889f, 0.8889f, 0.0f, true, true, 1.7778f, 0.0f)
                arcToRelative(0.8889f, 0.8889f, 0.0f, true, true, -1.7778f, 0.0f)
            }
        }
            .build()
        return _icInstagram!!
    }

private var _icInstagram: ImageVector? = null
