/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("NonAsciiCharacters")

package team.duckie.app.android.util.viewmodel

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.util.viewmodel.util.BasicState
import team.duckie.app.android.util.viewmodel.util.buildBasicViewModel

class ViewModelIntentTest {
    @Test
    fun `Waiting - Join - Leave 순으로 상태를 바꿈`() {
        val vm = buildBasicViewModel()
        expectThat(vm.currentState).isEqualTo(BasicState.Waiting)

        vm.updateState { BasicState.Join }
        expectThat(vm.currentState).isEqualTo(BasicState.Join)

        vm.updateState { BasicState.Leave }
        expectThat(vm.currentState).isEqualTo(BasicState.Leave)
    }

    @Test
    fun `Waiting - JoinRejected 순으로 상태를 바꿈`() {
        val vm = buildBasicViewModel()
        expectThat(vm.currentState).isEqualTo(BasicState.Waiting)

        vm.updateState {
            skip()
            @Suppress("UNREACHABLE_CODE")
            BasicState.Join
        }
        expectThat(vm.currentState).isEqualTo(BasicState.Waiting)

        vm.updateState { BasicState.JoinRejected }
        expectThat(vm.currentState).isEqualTo(BasicState.JoinRejected)
    }
}
