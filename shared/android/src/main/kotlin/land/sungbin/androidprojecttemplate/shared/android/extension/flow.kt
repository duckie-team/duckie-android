package land.sungbin.androidprojecttemplate.shared.android.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    builder: Flow<T>.() -> Flow<T> = { this },
    action: suspend CoroutineScope.(T) -> Unit,
) {
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        builder()
            .flowWithLifecycle(
                lifecycle = lifecycleOwner.lifecycle,
                minActiveState = minActiveState
            ).collect { value ->
                action(value)
            }
    }
}
