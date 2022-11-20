package team.duckie.app.android.data.model.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUserData(
    @SerializedName("username")
    val username: String = "doro"
) : Parcelable
