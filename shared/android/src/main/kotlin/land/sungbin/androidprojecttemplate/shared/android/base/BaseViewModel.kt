package land.sungbin.androidprojecttemplate.shared.android.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow


abstract class BaseViewModel<S : UiState, A : UiEffect> {

    private val initialState : S by lazy { createInitialState() }
    abstract fun createInitialState() : S

    private val _state : MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect : Channel<A> = Channel()
    val effect = _effect.receiveAsFlow()

    private val currentState: S
        get() = _state.value

    protected fun setState(reducer: S.() -> S) {
        val newState = currentState.reducer()
        _state.value = newState
    }

    protected suspend fun setEffect(effect: () -> A) {
        _effect.send(effect())
    }
}
