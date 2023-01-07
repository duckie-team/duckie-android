/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.viewmodel.util

import team.duckie.app.android.util.viewmodel.BaseViewModel

enum class BasicState {
    Waiting, Join, Leave, JoinRejected,
}

enum class BasicSideEffect {
    Greeting,
}

class BasicViewModel : BaseViewModel<BasicState, BasicSideEffect>(BasicState.Waiting)

@Suppress("NOTHING_TO_INLINE")
inline fun buildBasicViewModel(): BaseViewModel<BasicState, BasicSideEffect> {
    return BasicViewModel()
}
