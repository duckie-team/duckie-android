package land.sungbin.androidprojecttemplate.shared.domain.util

fun requireFieldNullMessage(fieldName: String) = "Require `$fieldName` field is null."
fun notAllowedValueMessage(value: Any) = "$value is not allowed here."
fun unknownResultMessage(result: Any) = "Unknown result: $result"

