/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.repository.ExamRepository
import javax.inject.Inject
@Immutable
class MakeExamUseCase @Inject constructor(
    private val examRepository: ExamRepository,
) {
    suspend operator fun invoke(examParam: ExamParam) = runCatching {
        examRepository.makeExam(examParam)
    }
}
