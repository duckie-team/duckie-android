/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val DURING_MILLIS = 10L
private const val TIMER_VELOCITY = 0.01f

class ProblemTimer(
    private val coroutineScope: CoroutineScope,
) {
    private val _remainingTime = MutableStateFlow(0f)
    val remainingTime: StateFlow<Float> = _remainingTime

    var totalTime = 0f
        private set

    private var timerJob: Job? = null

    fun start(count: Float) {
        timerJob?.cancel()
        timerJob = coroutineScope.launch(Dispatchers.IO) {
            _remainingTime.value = count
            while (_remainingTime.value > 0f) {
                delay(DURING_MILLIS)
                _remainingTime.value -= TIMER_VELOCITY
                totalTime += TIMER_VELOCITY
            }
        }
    }

    fun stop() {
        timerJob?.cancel()
    }
}
