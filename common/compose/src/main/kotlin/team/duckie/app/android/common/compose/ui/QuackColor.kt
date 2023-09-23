/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.ui.graphics.Color
import team.duckie.quackquack.material.QuackColor

val QuackColor.Companion.Gray35
    get() = QuackColor(
        value = Color(0xFF222222)
            .copy(alpha = 0.5f),
    )
