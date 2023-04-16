/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.report.datasource

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.exam.model.ExamInstanceBodyData
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor() : ReportRemoteDataSource {
    override suspend fun report(examId: Int) {
        val response = client.post {
            url("/reports")
            setBody(ExamInstanceBodyData(examId = examId))
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean()
        }
    }
}
