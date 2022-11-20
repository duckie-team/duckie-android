package team.duckie.app.android.domain.constants

import com.google.gson.annotations.SerializedName

enum class AccountType(
    val kor: String,
) {
    @SerializedName("DEFAULT")
    DEFAULT(
        kor = "정보가 없습니다",
    ),
    @SerializedName("KAKAO")
    KAKAO(
        kor = "카카오",
    ),
}
