/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.viewmodel.util

import team.duckie.app.android.util.viewmodel.BaseViewModel

@JvmInline
value class CounterState(val value: Int)

class CounterViewModel : BaseViewModel<CounterState, Nothing>(CounterState(0))

@Suppress("NOTHING_TO_INLINE")
inline fun buildCounterViewModel(): BaseViewModel<CounterState, Nothing> {
    return CounterViewModel()
}
