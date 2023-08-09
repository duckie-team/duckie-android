/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.kittinunf.fuel.Fuel
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.exam.datasource.ExamInfoDataSource
import team.duckie.app.android.data.exam.mapper.toData
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ExamInfoEntity
import team.duckie.app.android.data.exam.model.ExamMeBlocksResponse
import team.duckie.app.android.data.exam.model.ExamMeFollowingResponseData
import team.duckie.app.android.data.exam.model.ProfileExamDatas
import team.duckie.app.android.data.exam.paging.ExamMeFollowingPagingSource
import team.duckie.app.android.data.exam.paging.ProfileExamPagingSource
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.IgnoreExam
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ExamInfo
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.exam.repository.ExamRepository
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val fuel: Fuel,
    private val examInfoDataSource: ExamInfoDataSource,
) : ExamRepository {
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

    override suspend fun getExamMeFollowing(): Flow<PagingData<Exam>> {
        return Pager(
            config = PagingConfig(
                pageSize = ExamMePagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                ExamMeFollowingPagingSource(
                    getExamMeFollowing = { page ->
                        getExamMeFollowing(page)
                    },
                )
            },
        ).flow
    }

    @AllowMagicNumber
    private suspend fun getExamMeFollowing(page: Int): List<Exam> = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .get(
                "/exams/me/following",
                listOf("page" to page),
            )
            .responseString()

        val examMeFollowingResponse = responseCatchingFuel(
            response = response,
            parse = ExamMeFollowingResponseData::toDomain,
        )

        return@withContext examMeFollowingResponse.exams
    }

    private suspend fun getHeartExams(
        userId: Int,
        page: Int,
    ): List<ProfileExam> {
        val response = client.get {
            url("/exams/$userId/hearts")
            parameter("page", page)
        }
        return responseCatching(
            response = response,
            parse = ProfileExamDatas::toDomain,
        )
    }

    private suspend fun getExamsMe(userId: Int, page: Int): List<ProfileExam> {
        val response = client.get {
            url("/exams/$userId/me")
            parameter("page", page)
        }
        return responseCatching(
            response = response,
            parse = ProfileExamDatas::toDomain,
        )
    }

    override suspend fun getRecentExam(): List<ExamInfo> {
        return examInfoDataSource.getRecentExams().map(ExamInfoEntity::toDomain)
    }

    override suspend fun getHeartExam(userId: Int): Flow<PagingData<ProfileExam>> {
        return Pager(
            config = PagingConfig(
                pageSize = ExamMePagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                ProfileExamPagingSource(
                    getProfileExam = { page ->
                        getHeartExams(
                            userId = userId,
                            page = page,
                        )
                    },
                )
            },
        ).flow
    }

    override suspend fun getSubmittedExam(userId: Int): Flow<PagingData<ProfileExam>> {
        return Pager(
            config = PagingConfig(
                pageSize = ExamMePagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                ProfileExamPagingSource(
                    getProfileExam = { page ->
                        getExamsMe(
                            userId = userId,
                            page = page,
                        )
                    },
                )
            },
        ).flow
    }

    override suspend fun getIgnoreExams(): List<IgnoreExam> {
        val response = client.get("exams/me/blocks")
        return responseCatching(
            response = response,
            parse = ExamMeBlocksResponse::toDomain,
        )
    }

    override suspend fun cancelExamIgnore(examId: Int): Boolean {
        val response = client.delete("exam-block") {
            jsonBody {
                "targetId" withInt examId
            }
        }

        return responseCatching(response.status.value, response.bodyAsText()) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }

    override suspend fun getMadeExams(): List<ExamInfo> {
        return examInfoDataSource.getMadeExams().map(ExamInfoEntity::toDomain)
    }

    override suspend fun getSolvedExams(): List<ExamInfo> {
        return examInfoDataSource.getSolvedExams().map(ExamInfoEntity::toDomain)
    }

    override suspend fun getFavoriteExams(): List<ExamInfo> {
        return examInfoDataSource.getFavoriteExams().map(ExamInfoEntity::toDomain)
    }

    internal companion object {
        const val ExamMePagingPage = 10
    }
}
