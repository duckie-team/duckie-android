/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.exam.repository.ExamRepository
import javax.inject.Inject

@Immutable
class GetContinueMusicExamUseCase @Inject constructor(
    private val repository: ExamRepository,
) {
    suspend operator fun invoke(userId: Int): Flow<PagingData<ProfileExam>> {
        return repository.getContinueMusicExam(userId)
    }
}
