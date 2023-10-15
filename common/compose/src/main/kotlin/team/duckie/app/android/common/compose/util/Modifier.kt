/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import androidx.compose.ui.Modifier

fun Modifier.applyIf(condition: Boolean, applyModifier: Modifier.() -> Modifier): Modifier {
    return if (condition) this.applyModifier() else this
}
