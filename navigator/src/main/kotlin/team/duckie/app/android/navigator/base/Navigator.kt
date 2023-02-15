/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.navigator.base

import android.content.Context
import android.content.Intent

interface Navigator {
    fun intent(context: Context): Intent
}
