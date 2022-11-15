package land.sungbin.androidprojecttemplate.data.repository

import land.sungbin.androidprojecttemplate.data.datasource.remote.UserDataSource
import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeCategory
import land.sungbin.androidprojecttemplate.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun fetchCategories(): List<LikeCategory> {
        return userDataSource.getCategories()
    }
}