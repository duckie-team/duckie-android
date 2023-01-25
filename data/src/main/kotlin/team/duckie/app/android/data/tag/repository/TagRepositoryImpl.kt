/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.tag.repository

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitObject
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import javax.inject.Inject
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.repository.TagRepository

class TagRepositoryImpl @Inject constructor(private val fuel: Fuel) : TagRepository {
    override suspend fun create(name: String): Tag {
        val request = fuel
            .post("/tags")
            .body(
                body = buildJson {
                    "name" withString name
                },
            )
        val response = request.awaitObject<TagData>(jacksonDeserializerOf())
        return response.toDomain()
    }
}
