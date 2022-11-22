/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.compose

import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import team.duckie.app.android.util.viewmodel.BaseViewModel

/**
 * 현재 사용되고 있는 [BaseViewModel] 을 관리합니다.
 */
val LocalViewModel = staticCompositionLocalOf<BaseViewModel<*, *>> {
    noProvidedFor(name = "ViewModel")
}

/**
 * 요청한 [CompositionLocal] 이 provide 되지 않았을 때 사용합니다.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun noProvidedFor(name: String): Nothing {
    error("No $name provided")
}
