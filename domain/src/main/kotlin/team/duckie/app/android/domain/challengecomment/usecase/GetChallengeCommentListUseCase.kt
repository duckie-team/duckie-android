/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.challengecomment.usecase

import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.domain.challengecomment.repository.ChallengeCommentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetChallengeCommentListUseCase @Inject constructor(
    private val challengeCommentRepository: ChallengeCommentRepository,
) {

    suspend operator fun invoke(
        problemId: Int,
        order: CommentOrderType,
    ) = kotlin.runCatching {
        challengeCommentRepository.fetchChallengeCommentList(
            problemId = problemId,
            order = order,
        )
    }
}
