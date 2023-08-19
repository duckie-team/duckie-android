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

import team.duckie.app.android.data.challengecomment.model.CommentOrderTypeData
import team.duckie.app.android.domain.challengecomment.model.ChallengeCommentList

interface ChallengeCommentRemoteDataSource {
    suspend fun fetchChallengeCommentList(
        problemId: Int,
        order: CommentOrderTypeData,
        page: Int,
    ): ChallengeCommentList

    suspend fun postChallengeCommentHeart(commentId: Int): Int

    suspend fun deleteChallengeCommentHeart(commentId: Int): Boolean

    suspend fun reportChallengeComment(challengeId: Int): Boolean

    suspend fun writeChallengeComment(challengeId: Int, message: String): Boolean

    suspend fun deleteChallengeComment(commentId: Int): Boolean
}
