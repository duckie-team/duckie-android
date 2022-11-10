package land.sungbin.androidprojecttemplate.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<S : State, SE : SideEffect>(
    initialState: S,
) {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect: Channel<SE> = Channel()
    val effect = _effect.receiveAsFlow()

    protected val currentState: S
        get() = _state.value

    protected fun updateState(reducer: S.() -> S) {
        val newState = currentState.reducer()
        _state.value = newState
    }

    protected suspend fun postSideEffect(effect: () -> SE) {
        _effect.send(effect())
    }
}
