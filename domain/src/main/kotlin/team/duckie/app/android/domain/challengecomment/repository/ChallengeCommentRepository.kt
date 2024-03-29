/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.challengecomment.repository

import team.duckie.app.android.domain.challengecomment.model.ChallengeComment
import team.duckie.app.android.domain.challengecomment.model.ChallengeCommentList
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType

interface ChallengeCommentRepository {
    suspend fun fetchChallengeCommentList(
        problemId: Int,
        order: CommentOrderType,
    ): ChallengeCommentList

    suspend fun postChallengeCommentHeart(commentId: Int): Int

    suspend fun deleteChallengeCommentHeart(heartId: Int): Boolean

    suspend fun reportChallengeComment(commentId: Int): Boolean

    suspend fun writeChallengeComment(challengeId: Int, message: String): ChallengeComment

    suspend fun deleteChallengeComment(commentId: Int): Boolean
}
