package land.sungbin.androidprojecttemplate.shared.domain.extension

import io.github.jisungbin.logeukes.BuildConfig

// TODO: change to your message
fun Throwable.toMessage() = when (BuildConfig.DEBUG) {
    true -> message?.trim() ?: "Error message is null."
    else -> "일시적인 에러가 발생했어요.\n잠시 후 다시 시도해 주세요."
}
