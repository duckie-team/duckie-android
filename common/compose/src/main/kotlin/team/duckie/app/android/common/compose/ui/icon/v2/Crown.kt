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
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.QuackIcon

public val QuackIcon.Crown: ImageVector
    get() {
        if (_icCrown12 != null) {
            return _icCrown12!!
        }
        _icCrown12 = Builder(
            name = "IcCrown12",
            defaultWidth = 12.0.dp,
            defaultHeight = 12.0.dp,
            viewportWidth = 12.0f,
            viewportHeight = 12.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFF8300)),
                stroke = SolidColor(Color(0xFFFF8300)),
                strokeLineWidth = 1.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(3.344f, 9.0f)
                lineTo(2.0f, 5.118f)
                lineTo(4.112f, 6.0f)
                lineTo(6.032f, 4.0f)
                lineTo(8.016f, 6.0f)
                lineTo(10.0f, 5.118f)
                lineTo(8.72f, 9.0f)
                horizontalLineTo(3.344f)
                close()
            }
        }
            .build()
        return _icCrown12!!
    }

private var _icCrown12: ImageVector? = null
