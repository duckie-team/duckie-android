/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.challengecomment.repository

import team.duckie.app.android.data.challengecomment.datasource.ChallengeCommentRemoteDataSource
import team.duckie.app.android.data.challengecomment.mapper.toData
import team.duckie.app.android.domain.challengecomment.model.ChallengeComment
import team.duckie.app.android.domain.challengecomment.model.ChallengeCommentList
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.domain.challengecomment.repository.ChallengeCommentRepository
import javax.inject.Inject

class ChallengeCommentRepositoryImpl @Inject constructor(
    private val challengeCommentRemoteDataSource: ChallengeCommentRemoteDataSource,
) : ChallengeCommentRepository {
    override suspend fun fetchChallengeCommentList(
        problemId: Int,
        order: CommentOrderType,
    ): ChallengeCommentList {
        return challengeCommentRemoteDataSource.fetchChallengeCommentList(
            problemId = problemId,
            order = order.toData(),
            page = 1, // TODO(limsaehyun): 페이징 처리 필요함
        )
    }

    override suspend fun postChallengeCommentHeart(commentId: Int): Int {
        return challengeCommentRemoteDataSource.postChallengeCommentHeart(commentId = commentId)
    }

    override suspend fun deleteChallengeCommentHeart(heartId: Int): Boolean {
        return challengeCommentRemoteDataSource.deleteChallengeCommentHeart(heartId = heartId)
    }

    override suspend fun reportChallengeComment(commentId: Int): Boolean {
        return challengeCommentRemoteDataSource.reportChallengeComment(commentId = commentId)
    }

    override suspend fun writeChallengeComment(challengeId: Int, message: String): ChallengeComment {
        return challengeCommentRemoteDataSource.writeChallengeComment(
            challengeId = challengeId,
            message = message,
        )
    }

    override suspend fun deleteChallengeComment(commentId: Int): Boolean {
        return challengeCommentRemoteDataSource.deleteChallengeComment(commentId = commentId)
    }
}
