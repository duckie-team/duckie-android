package land.sungbin.androidprojecttemplate.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import land.sungbin.androidprojecttemplate.data.domain.User

object UserHolder {
    private var user: User? = null

    fun setUser(user: User?) {
        if (user != null) {
            val userJsonStr = Gson().toJson(user, User::class.java)
            PrefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER, userJsonStr)
        }
    }

    @JvmStatic
    fun getUser(): User? {
        var user = this.user
        if (user == null) {
            val userJsonStr =
                PrefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER)
            if (userJsonStr != null) {
                user = GsonBuilder()
                    .create()
                    .fromJson(userJsonStr, User::class.java)
                this.user = user
            }
        }
        return user
    }

    @JvmStatic
    fun hasSession(): Boolean {
        return getUser() != null
    }
}