/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.ignore.datasource

import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe
import javax.inject.Inject

class IgnoreRemoteDataSourceImpl @Inject constructor() : IgnoreRemoteDataSource {
    override suspend fun ignoreUser(targetId: Int) {
        val response = client.post("/user-block") {
            jsonBody {
                "targetId" withInt targetId
            }
        }

        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    override suspend fun deleteIgnoreUser(targetId: Int) {
        val response = client.delete {
            url("/user-block")
            setBody("targetId" to targetId)
        }

        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }
}
