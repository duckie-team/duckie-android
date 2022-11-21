/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import team.duckie.app.android.util.kotlin.DuckieDsl

/**
 * Reducer 에서 새로운 상태를 업데이트하기 위한 인터페이스
 */
@DuckieDsl
interface StateUpdater<T> {
    /**
     * 상태를 업데이트하거나 가져옵니다.
     *
     * [StateUpdater.skip] 를 통해 업데이트를 건너뛸 수 있습니다.
     */
    var state: T
}

internal class StateUpdaterScope<T>(
    private val sourceState: MutableStateFlow<T>,
) : StateUpdater<T> {
    override var state: T
        get() = sourceState.value
        set(value) {
            sourceState.update { value }
        }
}

@PublishedApi
internal class StateUpdaterSkipException : Exception()

/**
 * 상태를 업데이트하지 않고 현재 상태를 그대로 유지합니다.
 */
@Suppress("NOTHING_TO_INLINE", "UnusedReceiverParameter", "unused")
inline fun StateUpdater<*>.skip(): Nothing {
    throw StateUpdaterSkipException()
}
