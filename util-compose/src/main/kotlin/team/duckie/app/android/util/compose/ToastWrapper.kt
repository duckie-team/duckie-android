/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.lang.ref.WeakReference

class ToastWrapper(context: Context) {
    private val _context = WeakReference(context)
    private val context get() = _context.get()!!
    private val toast = Toast.makeText(this.context, "", Toast.LENGTH_SHORT)

    operator fun invoke(message: Any, length: Int = Toast.LENGTH_SHORT) {
        toast.run {
            setText(message.toString())
            duration = length
            show()
        }
    }
}

@Composable
fun rememberToast(): ToastWrapper {
    val context = LocalContext.current.applicationContext
    return remember(context) {
        ToastWrapper(context)
    }
}
