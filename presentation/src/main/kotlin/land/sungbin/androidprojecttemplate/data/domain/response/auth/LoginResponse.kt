package land.sungbin.androidprojecttemplate.data.domain.response.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import land.sungbin.androidprojecttemplate.data.domain.User

@Parcelize
data class LoginResponse(
    @SerializedName("user")
    val user: User
) : Parcelable
