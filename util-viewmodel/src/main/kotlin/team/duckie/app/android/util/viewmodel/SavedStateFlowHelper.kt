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
        val newValue = updater(savedState.value)
        if (savedStateHandle != null && key != null) {
            savedStateHandle[key] = newValue
        } else {
            (savedState as MutableStateFlow<T>).value = newValue
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
