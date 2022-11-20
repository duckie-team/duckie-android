package land.sungbin.androidprojecttemplate.shared.domain.extension

import land.sungbin.androidprojecttemplate.shared.domain.util.notAllowedValueMessage

fun Int.toBoolean() = when (this) {
    0 -> false
    1 -> true
    else -> throw IllegalStateException(notAllowedValueMessage(this))
}
