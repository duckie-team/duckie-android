/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> rememberUserInputState(
    defaultValue: T,
    updateState: (T) -> Unit,
    flowOperator: suspend Flow<T>.() -> Unit = { defaultUpdate(updateState) },
): MutableState<T> {
    var input = remember { mutableStateOf(defaultValue) }

    LaunchedEffect(key1 = input) {
        snapshotFlow { input.value }
            .flowOperator()
    }

    return input
}

private suspend fun <T> Flow<T>.defaultUpdate(
    updateState: (T) -> Unit,
) {
    collectLatest {
        updateState(it)
    }
}
