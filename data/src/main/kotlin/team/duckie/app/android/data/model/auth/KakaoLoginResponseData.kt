package team.duckie.app.android.data.model.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.parcelize.Parcelize

@Parcelize
data class KakaoLoginResponseData(
    @SerializedName("token")
    val token: OAuthToken,
) : Parcelable
