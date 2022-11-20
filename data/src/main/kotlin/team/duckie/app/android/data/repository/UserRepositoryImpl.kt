package team.duckie.app.android.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import team.duckie.app.data.datasource.local.PreferenceDataSource
import team.duckie.app.data.datasource.remote.UserDataSource
import team.duckie.app.domain.model.LoginUser
import team.duckie.app.domain.model.constraint.LikeCategory
import team.duckie.app.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val preferenceDataSource: PreferenceDataSource,
) : UserRepository {

    override suspend fun fetchCategories(): List<LikeCategory> {
        return userDataSource.getCategories()
    }

    override fun getUser(): LoginUser? {
        val userJsonStr = preferenceDataSource.getString(
            PreferenceDataSource.FILE_NAME,
            PreferenceDataSource.KEY_USER
        )
        return userJsonStr?.let {
            GsonBuilder().create().fromJson(userJsonStr, LoginUser::class.java)
        }
    }

    override fun setUser(user: LoginUser) {
        val userJsonStr = Gson().toJson(user, LoginUser::class.java)
        preferenceDataSource.setString(
            PreferenceDataSource.FILE_NAME,
            PreferenceDataSource.KEY_USER,
            userJsonStr
        )
    }

    override fun hasSession(): Boolean {
        return getUser() != null
    }
}
