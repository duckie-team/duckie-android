/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.compose

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> activityViewModel(
    key: String? = null,
    factory: ViewModelProvider.Factory? = null,
): VM = viewModel(
    VM::class.java,
    LocalContext.current as ComponentActivity,
    key,
    factory,
)
