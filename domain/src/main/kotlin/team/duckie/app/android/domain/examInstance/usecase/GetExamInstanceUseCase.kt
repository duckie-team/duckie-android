/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.examInstance.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.examInstance.repository.ExamInstanceRepository

@Immutable
class GetExamInstanceUseCase @Inject constructor(
    private val examInstanceRepository: ExamInstanceRepository,
) {
    suspend operator fun invoke(id: Int) = runCatching {
        examInstanceRepository.getExamInstance(id)
    }
}
