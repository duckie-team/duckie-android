/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.constant

import android.content.Context

typealias sharedRString = team.duckie.app.android.common.android.R.string

fun Context.getAppPackageName(): String {
    return getString(sharedRString.app_package_name)
}
