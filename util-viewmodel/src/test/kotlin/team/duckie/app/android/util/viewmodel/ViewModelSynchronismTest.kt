/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("NonAsciiCharacters")
@file:OptIn(ExperimentalCoroutinesApi::class)

package team.duckie.app.android.util.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.util.viewmodel.util.CounterState
import team.duckie.app.android.util.viewmodel.util.buildCounterViewModel

class ViewModelSynchronismTest {
    // FIXME(sungbin): 잘못된 동시성 테스트 코드. 코루틴 동시성 이해도 부족으로
    //                 잘못된 코드가 작성됨. 재공부하여 코드 수정이 필요함.
    @Test
    fun `여러 스레드에서 동시성이 보장됨`() {
        val vm = buildCounterViewModel()

        runBlocking {
            val times = 10_000
            val worker1 = launch(Dispatchers.IO.limitedParallelism(1)) {
                repeat(times) {
                    vm.updateState { prevState ->
                        CounterState(prevState.count + 1)
                    }
                    yield()
                }
            }
            val worker2 = launch(Dispatchers.IO.limitedParallelism(1)) {
                repeat(times) {
                    vm.updateState { prevState ->
                        CounterState(prevState.count - 1)
                    }
                    yield()
                }
            }
            joinAll(worker1, worker2)
        }

        val actual = vm.state.value.count
        val expected = 0

        expectThat(actual).isEqualTo(expected)
    }
}
