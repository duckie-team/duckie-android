/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import team.duckie.app.android.util.kotlin.OutOfDateApi
import javax.inject.Inject

@Immutable
class FetchFollowingTestUseCase @Inject constructor(
    private val repository: ExamRepository,
) {
    @OutOfDateApi
    suspend operator fun invoke() = runCatching {
        repository.getExamFollowing()
    }
}
