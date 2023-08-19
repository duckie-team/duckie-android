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

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data.challengecomment.model.CommentOrderTypeData
import team.duckie.app.android.data.challengecomment.model.GetChallengeCommentListResponse
import team.duckie.app.android.data.challengecomment.mapper.toDomain
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
}
