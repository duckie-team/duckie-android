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
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.icon.QuackIcon

public val QuackIcon.Order18: ImageVector
    get() {
        if (_order18 != null) {
            return _order18!!
        }
        _order18 = Builder(
            name = "Order18", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 18.0f, viewportHeight = 18.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF666666)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(6.0f, 4.5f)
                verticalLineTo(13.5f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF666666)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(3.75f, 6.75f)
                lineTo(6.0f, 4.5f)
                lineTo(8.25f, 6.75f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF666666)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.0f, 13.5f)
                lineTo(12.0f, 4.5f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF666666)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(14.25f, 11.25f)
                lineTo(12.0f, 13.5f)
                lineTo(9.75f, 11.25f)
            }
        }
        .build()
        return _order18!!
    }

private var _order18: ImageVector? = null
