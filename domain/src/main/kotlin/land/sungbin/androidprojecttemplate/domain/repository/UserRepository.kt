package land.sungbin.androidprojecttemplate.domain.repository

import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeCategory

interface UserRepository {

    suspend fun fetchCategories(): List<LikeCategory>
}