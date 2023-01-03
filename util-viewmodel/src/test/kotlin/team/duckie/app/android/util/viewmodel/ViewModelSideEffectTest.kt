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
import team.duckie.app.android.util.viewmodel.util.BasicSideEffect
import team.duckie.app.android.util.viewmodel.util.buildBasicViewModel

class ViewModelSideEffectTest {
    @Test
    fun `Greeting 부수 효과가 발생됨`() = runTest {
        val vm = buildBasicViewModel()

        launch {
            yield()
            vm.postSideEffect { BasicSideEffect.Greeting }
        }

        launch {
            vm.sideEffect.test {
                expectThat(awaitItem()).isEqualTo(BasicSideEffect.Greeting)
                ensureAllEventsConsumed()
                expectNoEvents()
            }
        }
    }
}
