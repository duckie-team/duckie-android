/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.challengecomment.usecase

import team.duckie.app.android.domain.challengecomment.repository.ChallengeCommentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteChallengeCommentUseCase @Inject constructor(
    private val challengeCommentRepository: ChallengeCommentRepository,
) {

    suspend operator fun invoke(commentId: Int) = runCatching {
        challengeCommentRepository.deleteChallengeComment(
            commentId = commentId,
        )
    }
}
