package land.sungbin.androidprojecttemplate.shared.compose.extension

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import land.sungbin.androidprojecttemplate.shared.compose.optin.LocalActivityUsageApi

@OptIn(LocalActivityUsageApi::class)
@Composable
inline fun <reified VM : ViewModel> activityViewModel(
    key: String? = null,
    factory: ViewModelProvider.Factory? = null,
) = viewModel(
    VM::class.java,
    LocalActivity.current,
    key,
    factory
)
