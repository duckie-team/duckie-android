/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.kotlin

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

fun Job.cancelChildrenAndItself() {
    cancelChildren()
    cancel()
}
