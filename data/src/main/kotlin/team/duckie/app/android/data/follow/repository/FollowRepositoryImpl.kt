/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.follow.repository

import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.follow.mapper.toData
import team.duckie.app.android.domain.follow.model.FollowBody
import team.duckie.app.android.domain.follow.repository.FollowRepository
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor() : FollowRepository {
    override suspend fun follow(followsBody: FollowBody): Boolean {
        val response = client.post("/follows") {
            setBody(followsBody.toData())
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    override suspend fun unFollow(followsBody: FollowBody): Boolean {
        val response = client.delete("/follows") {
            setBody(followsBody.toData())
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }
}
