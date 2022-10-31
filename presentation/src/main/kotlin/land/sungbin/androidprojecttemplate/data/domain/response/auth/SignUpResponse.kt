package land.sungbin.androidprojecttemplate.data.domain.response.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean
) : Parcelable
