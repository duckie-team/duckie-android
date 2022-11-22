/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * [CoroutineScope] 를 광역으로 제공하는 인터페이스
 */
interface CoroutineScopeContent {
    /**
     * 제공될 [CoroutineScope]
     */
    val coroutineScope: CoroutineScope
}

/**
 * 주어진 람다를 [CoroutineScope] 안에서 실행시킵니다.
 *
 * @receiver [CoroutineScope] 를 가져올 [CoroutineScopeContent]
 *
 * @param action 실행시킬 람다
 */
@Suppress("NOTHING_TO_INLINE")
inline fun CoroutineScopeContent.launch(noinline action: suspend CoroutineScope.() -> Unit): Job {
    return coroutineScope.launch(block = action)
}

/**
 * [CoroutineScopeContent] 의 구현을 제공합니다.
 */
@Composable
inline fun CoroutineScopeContent(
    content: @Composable CoroutineScopeContent.() -> Unit,
) {
    object : CoroutineScopeContent {
        override val coroutineScope = rememberCoroutineScope()
    }.content()
}
