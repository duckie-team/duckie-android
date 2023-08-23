/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.challengecomment.datasource

import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonBody
import team.duckie.app.android.data._util.toStringJsonMap
import team.duckie.app.android.data.challengecomment.mapper.toDomain
import team.duckie.app.android.data.challengecomment.model.ChallengeCommentResponse
import team.duckie.app.android.data.challengecomment.model.CommentOrderTypeData
import team.duckie.app.android.data.challengecomment.model.GetChallengeCommentListResponse
import team.duckie.app.android.domain.challengecomment.model.ChallengeComment
import team.duckie.app.android.domain.challengecomment.model.ChallengeCommentList
import javax.inject.Inject

class ChallengeCommentRemoteDataSourceImpl @Inject constructor() :
    ChallengeCommentRemoteDataSource {
    override suspend fun fetchChallengeCommentList(
        problemId: Int,
        order: CommentOrderTypeData,
        page: Int,
    ): ChallengeCommentList {
        val response = client.get("/challenge-comments") {
            parameter("problemId", problemId)
            parameter("order", order.type)
            parameter("page", page)
        }
        return responseCatching(
            response = response,
            parse = GetChallengeCommentListResponse::toDomain,
        )
    }

    override suspend fun postChallengeCommentHeart(commentId: Int): Int {
        val response = client.post("/challenge-comment-hearts") {
            jsonBody {
                "challengeCommentId" withInt commentId
            }
        }
        return responseCatching(
            statusCode = response.status.value,
            response = response.bodyAsText(),
        ) { body ->
            val json = body.toStringJsonMap()
            json["id"]?.toInt() ?: duckieResponseFieldNpe("id")
        }
    }

    override suspend fun deleteChallengeCommentHeart(heartId: Int): Boolean {
        val response = client.delete("/challenge-comment-hearts/$heartId")
        return responseCatchingReturnSuccess(response)
    }

    override suspend fun reportChallengeComment(commetnId: Int): Boolean {
        val response = client.post("/challenge-comment-reports") {
            jsonBody {
                "challengeCommentId" withInt commetnId
            }
        }
        return responseCatchingReturnSuccess(response)
    }

    override suspend fun writeChallengeComment(challengeId: Int, message: String): ChallengeComment {
        val response = client.post("/challenge-comments") {
            jsonBody {
                "message" withString message
                "challengeId" withInt challengeId
            }
        }
        return responseCatching(
            response = response,
            parse = ChallengeCommentResponse::toDomain,
        )
    }

    override suspend fun deleteChallengeComment(commentId: Int): Boolean {
        val response = client.delete("/challenge-comments/$commentId")
        return responseCatchingReturnSuccess(response)
    }

    /**
     * @return success boolean
     */
    private suspend fun responseCatchingReturnSuccess(
        response: HttpResponse,
    ): Boolean {
        return responseCatching(
            statusCode = response.status.value,
            response = response.bodyAsText(),
        ) { body ->
            val json = body.toStringJsonMap()
            json["success"]?.toBoolean() ?: duckieResponseFieldNpe("success")
        }
    }
}
