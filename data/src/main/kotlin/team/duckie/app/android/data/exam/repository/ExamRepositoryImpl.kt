/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.repository

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toJsonObject
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.exam.mapper.toData
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.DuckPowerResponse
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

// TODO(riflockle7): GET /exams/me/following API commit
class ExamRepositoryImpl @Inject constructor(/*private val fuel: Fuel*/) : ExamRepository {
    @OutOfDateApi
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

    @OutOfDateApi
    override suspend fun getExam(id: Int): Exam {
        val response = client.get {
            url("/exams/$id")
        }
        return responseCatching(response, ExamData::toDomain)
    }

    @OutOfDateApi
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

    @OutOfDateApi
    override suspend fun postExamInstance(examInstanceBody: ExamInstanceBody): Boolean {
        val response = client.post {
            url("/exam-instance")
            setBody(examInstanceBody.toData())
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    @OutOfDateApi
    override suspend fun postExamInstanceSubmit(
        id: Int,
        examInstanceSubmitBody: ExamInstanceSubmitBody,
    ): ExamInstanceSubmit {
        val response = client.post {
            url("/exam-instance/$id/submit")
            setBody(examInstanceSubmitBody.toData())
        }
        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            body.toJsonObject<ExamInstanceSubmitData>().toDomain()
        }
    }

    // TODO(limsaehyun): API 불안정하여 Mock 으로 구현
    // TODO(riflockle7): GET /exams/me/following API commit
    @AllowMagicNumber
    @OutOfDateApi
    override suspend fun getExamFollowing(page: Int): List<Exam> = withContext(Dispatchers.IO) {
//        val (_, response) = fuel
//            .post(
//                "/exams/me/following",
//                listOf("page" to page),
//            )
//            .responseString()
//
//        val examMeFollowingResponse = responseCatchingFuel(
//            response = response,
//            parse = ExamMeFollowingResponseData::toDomain,
//        )
//
//        return@withContext examMeFollowingResponse.exams

        val response = (0..6).map { exam ->
            ExamData(
                id = exam,
                title = "제 ${exam}회 도로 패션 영역",
                description = "설명",
                user = UserResponse(
                    id = 1,
                    nickName = "faker",
                    profileImageUrl = "https://img.freepik.com/free-icon/user_318-804790.jpg?w=2000",
                    duckPower = DuckPowerResponse(
                        id = 1,
                        tier = "덕력 20%",
                        tag = TagData(
                            id = 1,
                            name = "도로패션",
                        ),
                    ),
                ),
                thumbnailUrl = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                buttonTitle = "열심히 풀어주세요",
                certifyingStatement = "이것은 content 인비다",
                solvedCount = 1,
                answerRate = 0.17f,
                category = CategoryData(1, "카테고리"),
                mainTag = TagData(1, "방탄소년단"),
                type = "text",
            )
        }

        return@withContext response.fastMap(ExamData::toDomain)
    }
}
