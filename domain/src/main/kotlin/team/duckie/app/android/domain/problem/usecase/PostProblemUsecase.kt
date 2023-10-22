/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.problem.usecase

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.problem.model.ProblemBody
import team.duckie.app.android.domain.problem.repository.ProblemRepository
import javax.inject.Inject

@Immutable
class PostProblemUseCase @Inject constructor(
    private val repository: ProblemRepository,
) {
    suspend operator fun invoke(problemBody: ProblemBody): Result<Problem> {
        return runCatching {
            repository.postProblem(problemBody)
        }
    }
}
