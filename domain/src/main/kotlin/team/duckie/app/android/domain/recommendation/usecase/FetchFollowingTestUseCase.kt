/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.repository.ExamRepository
import javax.inject.Inject

@Immutable
class FetchFollowingTestUseCase @Inject constructor(
    private val repository: ExamRepository,
) {
    suspend operator fun invoke(page: Int = 1) = runCatching {
        repository.getExamFollowing(page = page)
    }
}
