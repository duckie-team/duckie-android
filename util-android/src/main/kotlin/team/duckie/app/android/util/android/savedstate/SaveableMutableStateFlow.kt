/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.android.savedstate

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SaveableMutableStateFlow<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    initialValue: T,
) : ReadWriteProperty<Any, T> {
    private val state = savedStateHandle.getStateFlow(key, initialValue)
    fun asStateFlow() = state

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return state.value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        savedStateHandle[key] = value
    }
}
