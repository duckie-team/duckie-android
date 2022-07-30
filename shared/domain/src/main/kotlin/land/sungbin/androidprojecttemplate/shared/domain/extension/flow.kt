package land.sungbin.androidprojecttemplate.shared.domain.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.defaultCatch(
    name: String? = null,
    action: (exception: IllegalStateException) -> Unit,
) = catch { cause ->
    val exception =
        IllegalStateException("${name ?: cause.tag} flow collect exception: ${cause.message}")
    action(exception)
}
