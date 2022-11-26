/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package team.duckie.app.android.util.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * 모든 ViewModel 의 기본적인 ViewModel 입니다.
 *
 * @param State ViewModel 의 상태
 * @param SideEffect ViewModel 에서 발생할 수 있는 부수 효과
 * @param initialState ViewModel 의 초기 상태
 */
@Suppress("UnnecessaryAbstractClass")
abstract class BaseViewModel<State, SideEffect>(initialState: State) {
    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)

    /**
     * 실시간 상태 업데이트를 [Flow] 로 구독합니다.
     */
    val state = mutableState.asStateFlow()

    private val mutableEffect: Channel<SideEffect> = Channel()

    /**
     * 실시간 부수 효과를 [Channel] 로 구독합니다.
     */
    val sideEffect = mutableEffect.receiveAsFlow()

    /**
     * 현재 상태를 조회합니다.
     */
    val currentState: State get() = state.value

    /**
     * 상태를 업데이트합니다.
     *
     * @param updater 상태를 업데이트하는 람다.
     * 새로운 상태를 반환하거나 skip 돼야 합니다.
     */
    fun updateState(updater: StateUpdater.(currentState: State) -> State) {
        try {
            mutableState.update { currentState ->
                StateUpdaterScope.updater(currentState)
            }
        } catch (ignore: StateUpdaterSkipException) {
            // Skip state update
        }
    }

    /**
     * 부수 효과를 발생시킵니다.
     *
     * @param effect 새로운 부수 효과
     */
    suspend fun postSideEffect(effect: () -> SideEffect) {
        mutableEffect.send(effect())
    }
}
