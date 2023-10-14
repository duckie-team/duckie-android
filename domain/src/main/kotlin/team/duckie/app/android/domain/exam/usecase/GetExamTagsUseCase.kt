/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.tag.model.Tag
import javax.inject.Inject

@Immutable
class GetExamTagsUseCase @Inject constructor(
    private val repository: ExamRepository,
) {
    suspend operator fun invoke(status: String = "FUNDING"): Result<List<Tag>> {
        return runCatching { repository.getTags(status) }
    }
}
