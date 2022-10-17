package land.sungbin.androidprojecttemplate.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import land.sungbin.androidprojecttemplate.data.domain.User
import javax.inject.Inject

class UserHolder @Inject constructor(
    private val prefUtil: PrefUtil
) {
    private var user: User? = null

    fun setUser(user: User?) {
        if (user != null) {
            val userJsonStr = Gson().toJson(user, User::class.java)
            prefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER, userJsonStr)
        }
    }

    fun getUser(): User? {
        var user = this.user
        if (user == null) {
            val userJsonStr =
                prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER)
            if (userJsonStr != null) {
                user = GsonBuilder()
                    .create()
                    .fromJson(userJsonStr, User::class.java)
                this.user = user
            }
        }
        return user
    }

    fun hasSession(): Boolean {
        return getUser() != null
    }
}