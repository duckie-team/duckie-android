/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.ui

import android.content.Intent

/**
 * Intent 에서 String 값을 반환한 후, 제거합니다.
 */
fun Intent.popStringExtra(key: String): String? {
    val value = this.getStringExtra(key)
    if (value != null) { this.removeExtra(key) }
    return value
}
