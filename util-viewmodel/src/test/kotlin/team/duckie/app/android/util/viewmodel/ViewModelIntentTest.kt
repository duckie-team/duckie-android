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
import kotlinx.coroutines.yield
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.util.viewmodel.util.BasicState
import team.duckie.app.android.util.viewmodel.util.buildBasicViewModel

class ViewModelIntentTest {
    @Test
    fun `Waiting - Join - Leave 순으로 상태를 바꿈`() = runTest {
        val vm = buildBasicViewModel()

        launch {
            yield()
            vm.updateState { BasicState.Join }
            vm.updateState { BasicState.Leave }
        }

        launch {
            vm.state.test {
                expectThat(awaitItem()).isEqualTo(BasicState.Waiting)
                expectThat(awaitItem()).isEqualTo(BasicState.Join)
                expectThat(awaitItem()).isEqualTo(BasicState.Leave)
                ensureAllEventsConsumed()
                expectNoEvents()
            }
        }
    }

    @Test
    fun `Waiting - JoinRejected 순으로 상태를 바꿈`() = runTest {
        val vm = buildBasicViewModel()

        launch {
            yield()
            vm.updateState {
                skip()
                @Suppress("UNREACHABLE_CODE")
                BasicState.Join
            }
            vm.updateState { BasicState.JoinRejected }
        }

        launch {
            vm.state.test {
                expectThat(awaitItem()).isEqualTo(BasicState.Waiting)
                expectThat(awaitItem()).isEqualTo(BasicState.JoinRejected)
                ensureAllEventsConsumed()
                expectNoEvents()
            }
        }
    }
}
