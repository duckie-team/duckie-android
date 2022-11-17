package land.sungbin.androidprojecttemplate.ui.main.setting.utils

enum class AccountType(
    val kor: String,
) {
    KAKAO(
        kor = "카카오",
    )
}

internal fun String.toAccountType(): AccountType =
    when(this) {
        "KAKAO" -> AccountType.KAKAO
        else -> throw IllegalArgumentException()
    }