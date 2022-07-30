package land.sungbin.androidprojecttemplate.shared.android.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = applicationContext,
    message = message,
    length = length
)

fun Fragment.toast(
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = requireContext(),
    message = message,
    length = length
)

fun toast(
    context: Context,
    messageBuilder: Context.() -> String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = context,
    message = messageBuilder(context),
    length = length
)

fun toast(
    context: Context,
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = context,
    message = message,
    length = length
)

private fun toastBuilder(
    context: Context,
    message: String,
    length: Int,
) {
    Toast.makeText(context, message, length).show()
}
