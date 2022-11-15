package land.sungbin.androidprojecttemplate.data.model.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.model.OAuthToken
import kotlinx.parcelize.Parcelize

@Parcelize
data class KakaoLoginResponse(
    @SerializedName("token")
    val token: OAuthToken,
) : Parcelable