/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Stable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction

@Stable
internal val ImeActionNext = KeyboardOptions(imeAction = ImeAction.Next)

@Stable
internal fun moveDownFocus(
    focusManager: FocusManager,
) = KeyboardActions(
    onNext = {
        focusManager.moveFocus(FocusDirection.Down)
    },
)
