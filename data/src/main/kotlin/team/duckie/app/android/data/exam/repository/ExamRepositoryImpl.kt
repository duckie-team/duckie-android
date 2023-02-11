/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.repository

import com.github.kittinunf.fuel.Fuel
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.exam.mapper.toData
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ExamMeFollowingResponseData
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import javax.inject.Inject

// TODO(riflockle7): GET /exams/me/following API commit
class ExamRepositoryImpl @Inject constructor(private val fuel: Fuel) : ExamRepository {
    override suspend fun makeExam(exam: ExamBody): Boolean {
        val response = client.post {
            url("/exams")
            setBody(exam.toData())
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    override suspend fun getExam(id: Int): Exam {
        val response = client.get {
            url("/exams/$id")
        }
        return responseCatching(response, ExamData::toDomain)
    }

    override suspend fun getExamThumbnail(examThumbnailBody: ExamThumbnailBody): String {
        val response = client.post {
            url("/exams/thumbnail")
            setBody(examThumbnailBody.toData())
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["url"] ?: duckieResponseFieldNpe("url")
        }
    }

    // TODO(riflockle7): GET /exams/me/following API commit
    @AllowMagicNumber
    override suspend fun getExamFollowing(page: Int?): List<Exam> = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .get(
                "/exams/me/following",
                page?.let { listOf("page" to page) },
            )
            .responseString()

        val examMeFollowingResponse = responseCatchingFuel(
            response = response,
            parse = ExamMeFollowingResponseData::toDomain,
        )

        return@withContext examMeFollowingResponse.exams
    }
}
