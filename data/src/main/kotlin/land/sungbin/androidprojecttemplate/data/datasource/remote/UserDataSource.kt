package land.sungbin.androidprojecttemplate.data.datasource.remote

import io.ktor.client.HttpClient
import land.sungbin.androidprojecttemplate.data.mapper.toDomain
import land.sungbin.androidprojecttemplate.data.model.LikeCategoryData
import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeCategory
import javax.inject.Inject

class UserDataSource @Inject constructor(
   private val client: HttpClient,
) {
    suspend fun getCategories(): List<LikeCategory> {
        /*val request = client.get {
           url(BaseUrl+"/router")
        }
        val body: List<Category> = request.body()
        */
        return listOf(
            LikeCategoryData(0, "연예인"),
            LikeCategoryData(1, "영화"),
            LikeCategoryData(2, "만화/애니"),
            LikeCategoryData(3, "웹툰"),
            LikeCategoryData(4, "게임"),
            LikeCategoryData(5, "밀리터리"),
            LikeCategoryData(6, "IT"),
            LikeCategoryData(7, "게임"),
            LikeCategoryData(8, "밀리터리"),
        ).map { it.toDomain() }
    }
}