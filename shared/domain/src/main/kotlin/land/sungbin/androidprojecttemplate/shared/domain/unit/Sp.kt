package land.sungbin.androidprojecttemplate.shared.domain.unit

@Suppress("MemberVisibilityCanBePrivate") // value must be public
@JvmInline
value class Sp(val value: Float) {
    // The adding of 0.5 is used to round UP to the nearest integer value.
    fun toInt() = (value + 0.5f).toInt()
}
