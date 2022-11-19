package land.sungbin.androidprojecttemplate.domain.constants

enum class AccountType(
    val kor: String,
) {
    DEFAULT(
        kor = "정보가 없습니다",
    ),
    KAKAO(
        kor = "카카오",
    ),
}

fun String.toAccountType(): AccountType =
    when(this) {
        "KAKAO" -> AccountType.KAKAO
        else -> AccountType.DEFAULT
    }