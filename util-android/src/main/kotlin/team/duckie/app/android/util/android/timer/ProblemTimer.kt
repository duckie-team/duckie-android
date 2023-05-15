/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.android.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProblemTimer(
    private val count: Int,
    private val coroutineScope: CoroutineScope,
    private val duringMillis: Long,
) {
    private val _remainingTime = MutableStateFlow(count)
    val remainingTime: StateFlow<Int> = _remainingTime

    var totalTime = 0
        private set

    private var timerJob: Job? = null

    fun start() {
        timerJob?.cancel()
        timerJob = coroutineScope.launch(Dispatchers.IO) {
            _remainingTime.value = count
            while (_remainingTime.value > 0) {
                delay(duringMillis)
                _remainingTime.value--
                totalTime++
            }
        }
    }

    fun stop() {
        timerJob?.cancel()
    }
}
