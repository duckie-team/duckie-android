/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.tag.repository

import com.github.kittinunf.fuel.Fuel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.PopularTagsData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.repository.TagRepository

class TagRepositoryImpl @Inject constructor(private val fuel: Fuel) : TagRepository {
    override suspend fun create(name: String): Tag = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .post("/tags")
            .body(
                body = buildJson {
                    "name" withString name
                },
            ).responseString()

        return@withContext responseCatchingFuel(
            response = response,
            parse = TagData::toDomain,
        )
    }

    override suspend fun getPopularTags(): ImmutableList<Tag> = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .get("/popularTags")
            .responseString()

        return@withContext responseCatchingFuel(
            response = response,
            parse = PopularTagsData::toDomain,
        )
    }

}
