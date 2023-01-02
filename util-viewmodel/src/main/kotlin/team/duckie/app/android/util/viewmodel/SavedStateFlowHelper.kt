/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class SavedStateFlowHelper<T>(
    private val savedStateHandle: SavedStateHandle? = null,
    private val key: String? = null,
    initialValue: T,
)/* : ReadWriteProperty<Any?, T> */ {
    private val savedState = if (savedStateHandle != null && key != null) {
        savedStateHandle.getStateFlow(key, initialValue)
    } else {
        MutableStateFlow(initialValue)
    }

    fun asStateFlow(): StateFlow<T> = savedState

    fun update(updater: (currentState: T) -> T) {
        if (savedStateHandle != null && key != null) {
            savedStateHandle[key] = updater(savedState.value)
        } else {
            (savedState as MutableStateFlow<T>).update(updater)
        }
    }

    /*
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
    */
}
