/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("NonAsciiCharacters")

package team.duckie.app.android.util.viewmodel

import android.widget.Button
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import team.duckie.app.android.util.viewmodel.activity.BaseViewModelSavedStateTestActivity

@RunWith(RobolectricTestRunner::class)
class BaseViewModelSavedStateTest {
    @Test
    fun `액티비티가 재생성돼도 ViewModel State 가 보존됨`() {
        val controller = Robolectric.buildActivity(BaseViewModelSavedStateTestActivity::class.java).also {
            it.setup()
        }
        val activity = controller.get()

        activity.findViewById<Button>(BaseViewModelSavedStateTestActivity.IncrementButtonViewId).performClick()
        activity.recreate()

        val actual = activity.vm.currentState
        val expected = BaseViewModelSavedStateTestActivity.ViewModelFinalState

        expectThat(actual).isEqualTo(expected)
    }
}
