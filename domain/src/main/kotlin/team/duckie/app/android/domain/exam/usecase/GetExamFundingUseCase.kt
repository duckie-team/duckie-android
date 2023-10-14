/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.ExamFunding
import team.duckie.app.android.domain.exam.repository.ExamRepository
import javax.inject.Inject

@Immutable
class GetExamFundingUseCase @Inject constructor(
    private val repository: ExamRepository,
) {
    suspend operator fun invoke(tagId: Int?, page: Int, limit: Int?): Result<List<ExamFunding>> {
        return runCatching { repository.getFunding(tagId, page, limit) }
    }
}
