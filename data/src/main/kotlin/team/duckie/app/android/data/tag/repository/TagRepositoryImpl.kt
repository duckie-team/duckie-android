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
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import javax.inject.Inject
import team.duckie.app.android.data._datasource.MoshiBuilder
import team.duckie.app.android.data._exception.util.responseCatchingFuelObject
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.repository.TagRepository

class TagRepositoryImpl @Inject constructor(private val fuel: Fuel) : TagRepository {
    // TODO(riflockle7): 문제 만들기 화면에서 잘 동작하는지 확인 필요
    override suspend fun create(name: String): Tag = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .post("/tags")
            .body(
                body = buildJson {
                    "name" withString name
                },
            )
            .responseObject<TagData>(
                deserializer = moshiDeserializerOf(MoshiBuilder.adapter(TagData::class.java)),
            )

        return@withContext responseCatchingFuelObject(
            response = response,
            parse = TagData::toDomain,
        )
    }
}
