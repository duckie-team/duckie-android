/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.examInstance.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.examInstance.repository.ExamInstanceRepository
import javax.inject.Inject

@Immutable
class GetSolvedExamInstance @Inject constructor(
    private val examInstanceRepository: ExamInstanceRepository,
) {
    suspend operator fun invoke(id: Int) = examInstanceRepository.getSolvedExamInstance(id)
}
