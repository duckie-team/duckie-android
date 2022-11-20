package land.sungbin.androidprojecttemplate.shared.domain.extension

fun String.convertNullableString() = if (this == "null") null else this
