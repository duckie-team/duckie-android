/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.common

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

internal fun Modifier.verticalScrollModifierAsCondition(
    condition: Boolean,
): Modifier = composed {
    if (condition) {
        this.verticalScroll(rememberScrollState())
    } else {
        this
    }
}
