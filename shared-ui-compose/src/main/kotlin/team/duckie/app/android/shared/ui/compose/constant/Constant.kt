/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose.constant

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

typealias sharedRString = team.duckie.app.android.shared.ui.compose.R.string
// TODO(riflockle7): 추후 사용 시 활성화할 것
// typealias sharedRDrawable = team.duckie.app.android.shared.ui.compose.R.drawable

@Composable
fun getAppPackageName(): String {
    return stringResource(id = sharedRString.app_package_name)
}
