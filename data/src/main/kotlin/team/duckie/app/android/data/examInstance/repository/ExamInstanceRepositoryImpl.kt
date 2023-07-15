/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.examInstance.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.kittinunf.fuel.Fuel
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data._util.toJsonObject
import team.duckie.app.android.data.exam.mapper.toData
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitData
import team.duckie.app.android.data.examInstance.mapper.toDomain
import team.duckie.app.android.data.examInstance.model.ExamInstanceData
import team.duckie.app.android.data.examInstance.model.ProfileExamInstanceDatas
import team.duckie.app.android.data.examInstance.paging.ProfileExamInstancePagingSource
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.examInstance.model.ExamInstance
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.domain.examInstance.repository.ExamInstanceRepository
import javax.inject.Inject

class ExamInstanceRepositoryImpl @Inject constructor(
    private val fuel: Fuel,
) : ExamInstanceRepository {
    override suspend fun getExamInstance(
        examInstanceId: Int,
    ): ExamInstance = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .get("/exam-instance/$examInstanceId")
            .responseString()

        return@withContext responseCatchingFuel(
            response = response,
            parse = ExamInstanceData::toDomain,
        )
    }

    override suspend fun postExamInstance(examInstanceBody: ExamInstanceBody): ExamInstance =
        withContext(Dispatchers.IO) {
            val (_, response) = fuel
                .post(path = "/exam-instance")
                .body(
                    body = buildJson {
                        "examId" withInt examInstanceBody.examId
                    },
                )
                .responseString()

            return@withContext responseCatchingFuel(
                response = response,
                parse = ExamInstanceData::toDomain,
            )
        }

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

    override suspend fun getSolvedExamInstance(userId: Int): Flow<PagingData<ProfileExamInstance>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ProfileExamInstancePagingSource(
                    getExamInstanceMe = { getExamInstanceMe(userId, it) },
                )
            },
        ).flow
    }

    private suspend fun getExamInstanceMe(userId: Int, page: Int): List<ProfileExamInstance> {
        val response = client.get {
            url("/exam-instance/$userId/me")
            parameter("page", page)
        }
        return responseCatching(
            response = response,
            parse = ProfileExamInstanceDatas::toDomain,
        )
    }
}
