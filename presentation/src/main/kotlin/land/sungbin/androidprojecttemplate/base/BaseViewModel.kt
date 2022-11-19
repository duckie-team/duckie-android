package land.sungbin.androidprojecttemplate.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<State, SideEffect>(
    initialState: State,
) {
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect: Channel<SideEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected val currentState: State
        get() = _state.value

    protected fun updateState(reducer: State.() -> State) {
        val newState = currentState.reducer()
        _state.value = newState
    }

    protected suspend fun postSideEffect(effect: () -> SideEffect) {
        _effect.send(effect())
    }
}
