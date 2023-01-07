/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.tag.repository

import io.ktor.client.call.body
import io.ktor.client.request.post
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.repository.TagRepository

class TagRepositoryImpl @Inject constructor() : TagRepository {
    override suspend fun create(name: String): Tag {
        val response = client.post("/tags") {
            jsonBody {
                "name" withString name
            }
        }
        return responseCatching(
            response = response.body(),
            parse = TagData::toDomain,
        )
    }
}
