package land.sungbin.androidprojecttemplate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import land.sungbin.androidprojecttemplate.constants.UiConstant.WHITE_GRADIENT_HEIGHT

fun Modifier.whiteGradient() = Modifier
    .height(WHITE_GRADIENT_HEIGHT)
    .background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.White,
            )
        )
    )