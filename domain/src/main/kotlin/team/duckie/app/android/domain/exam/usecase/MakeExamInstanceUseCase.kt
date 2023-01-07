/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import androidx.compose.runtime.Immutable
import javax.inject.Inject
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.util.kotlin.OutOfDateApi

@Immutable
class MakeExamInstanceUseCase @Inject constructor(
    private val examRepository: ExamRepository,
) {
    @OptIn(OutOfDateApi::class)
    suspend operator fun invoke(body: ExamInstanceBody) = runCatching {
        examRepository.postExamInstance(body)
    }
}
