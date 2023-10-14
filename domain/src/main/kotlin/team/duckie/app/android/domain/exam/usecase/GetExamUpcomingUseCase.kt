/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.home.model.HomeFunding
import javax.inject.Inject

@Immutable
class GetExamUpcomingUseCase @Inject constructor(
    private val repository: ExamRepository,
) {
    suspend operator fun invoke(page: Int): Result<List<HomeFunding>> {
        return runCatching { repository.getUpcoming(page) }
    }
}
