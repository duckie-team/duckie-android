package land.sungbin.androidprojecttemplate.shared.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExplicitGroupsComposable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface CoroutineScopeContent {
    val coroutineScope: CoroutineScope
}

@Suppress("NOTHING_TO_INLINE")
inline fun CoroutineScopeContent.launch(noinline action: suspend CoroutineScope.() -> Unit): Job {
    return coroutineScope.launch(block = action)
}

@Composable
@ExplicitGroupsComposable
inline fun CoroutineScopeContent(
    content: @Composable CoroutineScopeContent.() -> Unit,
) {
    object : CoroutineScopeContent {
        override val coroutineScope = rememberCoroutineScope()
    }.content()
}