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

public val QuackIcon.Clock: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = Builder(
            name = "Vector",
            defaultWidth = 12.0.dp,
            defaultHeight = 12.0.dp,
            viewportWidth = 12.0f,
            viewportHeight = 12.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF222222)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(6.0f, 1.2f)
                curveTo(4.727f, 1.2f, 3.5061f, 1.7057f, 2.6059f, 2.6059f)
                curveTo(1.7057f, 3.5061f, 1.2f, 4.727f, 1.2f, 6.0f)
                curveTo(1.2f, 7.273f, 1.7057f, 8.4939f, 2.6059f, 9.3941f)
                curveTo(3.5061f, 10.2943f, 4.727f, 10.8f, 6.0f, 10.8f)
                curveTo(7.273f, 10.8f, 8.4939f, 10.2943f, 9.3941f, 9.3941f)
                curveTo(10.2943f, 8.4939f, 10.8f, 7.273f, 10.8f, 6.0f)
                curveTo(10.8f, 4.727f, 10.2943f, 3.5061f, 9.3941f, 2.6059f)
                curveTo(8.4939f, 1.7057f, 7.273f, 1.2f, 6.0f, 1.2f)
                close()
                moveTo(0.0f, 6.0f)
                curveTo(0.0f, 2.6862f, 2.6862f, 0.0f, 6.0f, 0.0f)
                curveTo(9.3138f, 0.0f, 12.0f, 2.6862f, 12.0f, 6.0f)
                curveTo(12.0f, 9.3138f, 9.3138f, 12.0f, 6.0f, 12.0f)
                curveTo(2.6862f, 12.0f, 0.0f, 9.3138f, 0.0f, 6.0f)
                close()
                moveTo(6.0f, 2.4f)
                curveTo(6.1591f, 2.4f, 6.3117f, 2.4632f, 6.4243f, 2.5757f)
                curveTo(6.5368f, 2.6883f, 6.6f, 2.8409f, 6.6f, 3.0f)
                verticalLineTo(5.7516f)
                lineTo(8.2242f, 7.3758f)
                curveTo(8.3335f, 7.489f, 8.394f, 7.6405f, 8.3926f, 7.7978f)
                curveTo(8.3912f, 7.9552f, 8.3281f, 8.1056f, 8.2169f, 8.2169f)
                curveTo(8.1056f, 8.3281f, 7.9552f, 8.3912f, 7.7978f, 8.3926f)
                curveTo(7.6405f, 8.394f, 7.489f, 8.3335f, 7.3758f, 8.2242f)
                lineTo(5.5758f, 6.4242f)
                curveTo(5.4633f, 6.3117f, 5.4f, 6.1591f, 5.4f, 6.0f)
                verticalLineTo(3.0f)
                curveTo(5.4f, 2.8409f, 5.4632f, 2.6883f, 5.5757f, 2.5757f)
                curveTo(5.6883f, 2.4632f, 5.8409f, 2.4f, 6.0f, 2.4f)
                close()
            }
        }
            .build()
        return _vector!!
    }

private var _vector: ImageVector? = null
