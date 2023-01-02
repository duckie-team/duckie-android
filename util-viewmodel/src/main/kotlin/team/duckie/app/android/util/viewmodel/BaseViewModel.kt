/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package team.duckie.app.android.util.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * 모든 ViewModel 의 기본적인 ViewModel 입니다.
 *
 * @param State ViewModel 의 상태
 * @param SideEffect ViewModel 에서 발생할 수 있는 부수 효과
 * @param initialState ViewModel 의 초기 상태
 */
@Suppress("UnnecessaryAbstractClass")
abstract class BaseViewModel<State, SideEffect>(
    initialState: State,
    savedStateHandle: SavedStateHandle? = null,
) {
    private val _state = SavedStateFlowHelper(
        savedStateHandle = savedStateHandle,
        key = StateSavedStateKey,
        initialValue = initialState,
    )
    val state: StateFlow<State> = _state.asStateFlow()
    val currentState: State get() = state.value

    private val _sideEffect: Channel<SideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    // TODO(sungbin): 동시성 보장
    fun updateState(updater: StateUpdater.(currentState: State) -> State) {
        try {
            _state.update { currentState ->
                StateUpdaterScope.updater(currentState)
            }
        } catch (ignore: StateUpdaterSkipException) {
            // Skip state update
        }
    }

    suspend fun postSideEffect(effect: () -> SideEffect) {
        _sideEffect.send(effect())
    }

    private companion object {
        const val StateSavedStateKey = "BaseViewModel_State"
    }
}
