/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.viewmodel

/**
 * Reducer 스코프를 나타냅니다.
 */
interface StateUpdater

internal object StateUpdaterScope : StateUpdater

@PublishedApi
internal class StateUpdaterSkipException : Exception()

/**
 * 상태를 업데이트하지 않고 현재 상태를 그대로 유지합니다.
 */
@Suppress("NOTHING_TO_INLINE", "UnusedReceiverParameter", "unused")
inline fun StateUpdater.skip(): Nothing {
    throw StateUpdaterSkipException()
}
