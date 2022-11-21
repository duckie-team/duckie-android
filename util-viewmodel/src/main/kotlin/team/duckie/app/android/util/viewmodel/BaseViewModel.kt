/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<State, SideEffect>(initialState: State) {
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect: Channel<SideEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    val currentState: State get() = _state.value

    fun updateState(reducer: State.() -> State) {
        val newState = currentState.reducer()
        _state.value = newState
    }

    suspend fun postSideEffect(effect: () -> SideEffect) {
        _effect.send(effect())
    }
}
