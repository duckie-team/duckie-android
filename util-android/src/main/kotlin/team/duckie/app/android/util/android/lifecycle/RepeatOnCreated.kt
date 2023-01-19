/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.android.lifecycle

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

// FIXME(sungbin): `launchWhenCreated` + `repeatOnLifecycle` 가 best 한 조합일까?
fun ComponentActivity.repeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenCreated {
        repeatOnLifecycle(
            state = Lifecycle.State.CREATED,
            block = block,
        )
    }
}
