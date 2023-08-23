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

val QuackIcon.IcTrash24: ImageVector
    get() {
        if (_icTrash24 != null) {
            return _icTrash24!!
        }
        _icTrash24 = Builder(
            name = "IcTrash24",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
                viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFFFF2929)),
                    strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(5.0f, 7.0f)
                lineTo(19.0f, 7.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFFFF2929)),
                    strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(6.0f, 10.0f)
                verticalLineTo(20.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(10.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFFFF2929)),
                    strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(9.0f, 7.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(7.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFFFF2929)),
                    strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(10.0f, 12.0f)
                verticalLineTo(16.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFFFF2929)),
                    strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(14.0f, 12.0f)
                verticalLineTo(16.0f)
            }
        }
        .build()
        return _icTrash24!!
    }

private var _icTrash24: ImageVector? = null
