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

public val QuackIcon.Download: ImageVector
    get() {
        if (_icDownload24 != null) {
            return _icDownload24!!
        }
        _icDownload24 = Builder(
            name = "IcDownload24",
            defaultWidth = 25.0.dp,
            defaultHeight =
                24.0.dp,
                    viewportWidth = 25.0f,
            viewportHeight = 24.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF222222)),
                    strokeLineWidth = 1.0f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(4.5f, 11.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(20.5f)
                verticalLineTo(11.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF222222)),
                    strokeLineWidth = 1.0f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(9.0f, 11.1988f)
                lineTo(12.4997f, 15.0f)
                lineTo(16.0f, 11.1988f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF222222)),
                    strokeLineWidth = 1.0f,
                strokeLineCap = Round,
                        strokeLineJoin =
                    StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(12.5f, 5.0f)
                lineTo(12.5f, 15.0f)
            }
        }
        .build()
        return _icDownload24!!
    }

private var _icDownload24: ImageVector? = null
