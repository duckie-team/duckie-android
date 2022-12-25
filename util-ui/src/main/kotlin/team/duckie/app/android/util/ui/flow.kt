/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * 주어진 [Flow] 를 [Lifecycle] 에 맞게 collect 합니다.
 *
 * @param lifecycle The [Lifecycle] where the restarting collecting from `this` flow work will be
 * kept alive.
 * @param minActiveState [Lifecycle.State] in which the upstream flow gets collected. The
 * collection will stop if the lifecycle falls below that state, and will restart if it's in that
 * state again.
 * @param collector The [FlowCollector] that will receive the values from the upstream flow.
 */
suspend fun <T> Flow<T>.collectWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    collector: FlowCollector<T>,
) {
    this
        .flowWithLifecycle(
            lifecycle = lifecycle,
            minActiveState = minActiveState,
        )
        .collect(collector)
}
