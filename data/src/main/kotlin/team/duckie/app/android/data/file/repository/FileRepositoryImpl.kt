/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.file.repository

import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import java.io.File
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.domain.file.repository.FileRepository
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe

class FileRepositoryImpl @Inject constructor() : FileRepository {
    override suspend fun upload(file: File, type: String): String {
        val response = client.post("/files") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("type", type)
                        append("file", file.readBytes())
                    }
                )
            )
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }
        return responseCatching<String, String>(response.body()) { body ->
            val json = body.toStringJsonMap()
            json["url"] ?: duckieResponseFieldNpe("url")
        }
    }
}
