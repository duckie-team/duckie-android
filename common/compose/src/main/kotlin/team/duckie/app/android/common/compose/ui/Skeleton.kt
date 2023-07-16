/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import team.duckie.quackquack.material.QuackColor

fun Modifier.skeleton(
    visible: Boolean,
    shape: Shape = RoundedCornerShape(8.dp),
) = then(
    Modifier.placeholder(
        visible = visible,
        color = QuackColor.Gray3.value,
        shape = shape,
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = QuackColor.Gray4.value,
            animationSpec = PlaceholderDefaults.shimmerAnimationSpec,
        ),
    ),
)
