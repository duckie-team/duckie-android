package land.sungbin.androidprojecttemplate.shared.domain.extension

inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
    run()
} else this

inline fun <T> T.runIfBuilder(condition: (T) -> Boolean, run: T.() -> T) = if (condition(this)) {
    run()
} else this
