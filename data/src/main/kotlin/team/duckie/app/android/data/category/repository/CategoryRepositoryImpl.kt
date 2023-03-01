/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.category.repository

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toJsonMap
import team.duckie.app.android.data.category.mapper.toDomain
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.category.repository.CategoryRepository
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun getCategories(withPopularTags: Boolean?): List<Category> {
        val response = client.get {
            url("/categories")
            withPopularTags?.run { parameter("withPopularTags", this) }
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json: Map<String, List<CategoryData>> = body.toJsonMap()
            json["categories"]?.fastMap(CategoryData::toDomain) ?: duckieResponseFieldNpe("categories")
        }
    }
}
