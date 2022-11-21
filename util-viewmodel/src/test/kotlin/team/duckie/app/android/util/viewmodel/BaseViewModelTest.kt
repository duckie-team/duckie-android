/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("NonAsciiCharacters")
@file:OptIn(ExperimentalCoroutinesApi::class)

package team.duckie.app.android.util.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BaseViewModelTest {
    private enum class State {
        Waiting, Join, Leave, JoinRejected,
    }

    private enum class SideEffect {
        Greeting,
    }

    private class ViewModel : BaseViewModel<State, SideEffect>(State.Waiting)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun buildViewModel(): BaseViewModel<State, SideEffect> {
        return ViewModel()
    }

    @Test
    fun `Waiting - Join - Leave 순으로 상태를 바꿈`() {
        val vm = buildViewModel()
        expectThat(vm.currentState).isEqualTo(State.Waiting)

        vm.reduce {
            state = State.Join
        }
        expectThat(vm.currentState).isEqualTo(State.Join)

        vm.reduce {
            state = State.Leave
        }
        expectThat(vm.currentState).isEqualTo(State.Leave)
    }

    @Suppress("UNREACHABLE_CODE")
    @Test
    fun `Waiting - JoinRejected 순으로 상태를 바꿈`() {
        val vm = buildViewModel()
        expectThat(vm.currentState).isEqualTo(State.Waiting)

        vm.reduce {
            skip()
            state = State.Join
        }
        expectThat(vm.currentState).isEqualTo(State.Waiting)

        vm.reduce {
            state = State.JoinRejected
        }
        expectThat(vm.currentState).isEqualTo(State.JoinRejected)
    }

    @Test
    fun `Greeting 부수 효과가 발생됨`() = runTest {
        val vm = buildViewModel()

        launch {
            vm.effect.test {
                expectThat(awaitItem()).isEqualTo(SideEffect.Greeting)
            }
        }

        vm.postSideEffect { SideEffect.Greeting }
    }
}
