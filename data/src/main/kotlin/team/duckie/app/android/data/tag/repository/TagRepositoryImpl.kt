/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.tag.repository

import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._datasource.UnAuthorInterceptorFuelClient
import team.duckie.app.android.data._datasource.bodyAsText
import team.duckie.app.android.data._exception.util.responseCatching
import javax.inject.Inject
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data._util.jsonMapper
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.repository.TagRepository

class TagRepositoryImpl @Inject constructor(
    @UnAuthorInterceptorFuelClient private val fuel: Fuel,
) : TagRepository {
    override suspend fun create(name: String): Tag = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .post("/tags")
            .body(
                body = buildJson {
                    "name" withString name
                },
            ).responseString()

        return@withContext responseCatching(
            response.statusCode,
            response.bodyAsText(),
        ) { jsonMapper.readValue(response.bodyAsText(), TagData::class.java).toDomain() }
    }
}
