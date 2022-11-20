package land.sungbin.androidprojecttemplate.shared.android.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

fun LifecycleOwner.launchedWhenCreated(action: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenCreated {
        action()
    }
}
