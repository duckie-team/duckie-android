/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.repository.ExamRepository
import javax.inject.Inject

/**
 * 최근에 덕질한 시험을 가져오는 유즈케이스 입니다.
 */
@Immutable
class GetRecentExamUseCase @Inject constructor(
    private val repository: ExamRepository,
) {
    operator fun invoke() = runCatching {
        repository.getRecentExam()
    }
}
