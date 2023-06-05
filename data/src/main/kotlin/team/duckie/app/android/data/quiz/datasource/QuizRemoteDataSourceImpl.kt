/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.quiz.datasource

import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.quiz.mapper.toDomain
import team.duckie.app.android.data.quiz.model.GetQuizResponse
import team.duckie.app.android.data.quiz.model.PatchQuizBody
import team.duckie.app.android.data.quiz.model.PostQuizBody
import team.duckie.app.android.data.quiz.model.PostQuizResponse
import team.duckie.app.android.domain.quiz.model.Quiz
import team.duckie.app.android.domain.quiz.model.QuizResult
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import javax.inject.Inject

class QuizRemoteDataSourceImpl @Inject constructor() : QuizDataSource {
    override suspend fun postQuiz(examId: Int): Quiz {
        val response = client.post {
            url("/challenges")
            setBody(PostQuizBody(examId = examId))
        }
        return responseCatching(
            response,
            PostQuizResponse::toDomain,
        )
    }

    override suspend fun getQuizs(examId: Int): QuizResult {
        val response = client.get {
            url("/challenges/$examId")
        }
        return responseCatching(
            response,
            GetQuizResponse::toDomain,
        )
    }

    override suspend fun patchQuiz(
        examId: Int,
        body: PatchQuizBody,
    ): Boolean {
        val response = client.patch {
            url("/challenges/$examId")
            setBody(body)
        }
        return responseCatching(response.status.value, response.bodyAsText()) { responseBody ->
            val json = responseBody.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }
}
