package team.duckie.app.android.domain.repository

import team.duckie.app.domain.model.LoginUser
import team.duckie.app.domain.model.constraint.LikeCategory

interface UserRepository {

    suspend fun fetchCategories(): List<LikeCategory>

    fun getUser(): LoginUser?

    fun setUser(user: LoginUser)

    fun hasSession(): Boolean
}
